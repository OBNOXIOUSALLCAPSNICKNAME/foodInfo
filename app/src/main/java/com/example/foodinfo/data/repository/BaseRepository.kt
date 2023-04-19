package com.example.foodinfo.data.repository

import com.example.foodinfo.R
import com.example.foodinfo.domain.State
import com.example.foodinfo.data.remote.NetworkResponse
import com.example.foodinfo.utils.ApiResponse
import com.example.foodinfo.utils.ErrorCodes
import com.example.foodinfo.utils.NoDataException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow


abstract class BaseRepository {

    /**
     * Wrapper interface that helps [getData] to properly handle different data sources
     */
    internal sealed interface DataSource<T> {
        data class Remote<T : Any>(val response: ApiResponse<T>) : DataSource<T>
        data class Local<T>(val data: T) : DataSource<T>
        data class LocalFlow<T>(val flow: Flow<T>) : DataSource<T>
        object Empty : DataSource<Unit>
    }


    private inline fun <remoteT, localInT> fetchRemote(
        crossinline dataProvider: suspend () -> DataSource<remoteT>,
        crossinline mapDelegate: (remoteT) -> localInT
    ) = flow<State<localInT>> {
        emit(State.Initial())

        try {
            when (val source = dataProvider()) {
                is DataSource.Remote -> {
                    if (source.response is NetworkResponse.Success) {
                        emit(mapData(source.response.result, ErrorCodes.STATE_REMOTE_MAPPING, mapDelegate))
                    } else {
                        with(source.response as NetworkResponse.Error) {
                            emit(State.Failure(messageID, throwable, code))
                        }
                    }
                }
                is DataSource.Empty  -> {
                    emit(State.Failure(R.string.error_no_data, NoDataException(), ErrorCodes.STATE_NO_DATA))
                }
                else                 -> {
                    throw IllegalArgumentException(
                        "Unsupported remoteDataProvider type: ${source.javaClass.simpleName}."
                    )
                }
            }
        } catch (e: Exception) {
            emit(State.Failure(R.string.error_unknown, e, ErrorCodes.STATE_REMOTE_UNKNOWN))
        }
    }

    private inline fun <localInT, modelT> fetchLocal(
        crossinline dataProvider: suspend () -> DataSource<localInT>,
        crossinline mapDelegate: (localInT) -> modelT
    ) = flow {
        emit(State.Initial())

        try {
            when (val source = dataProvider()) {
                is DataSource.Local     -> {
                    emit(mapData(source.data, ErrorCodes.STATE_LOCAL_MAPPING, mapDelegate))
                }
                is DataSource.LocalFlow -> {
                    source.flow.collect { data ->
                        emit(mapData(data, ErrorCodes.STATE_LOCAL_MAPPING, mapDelegate))
                    }
                }
                else                    -> {
                    throw IllegalArgumentException(
                        "Unsupported localDataProvider type: ${source.javaClass.simpleName}."
                    )
                }
            }
        } catch (e: Exception) {
            emit(State.Failure(R.string.error_unknown, e, ErrorCodes.STATE_LOCAL_UNKNOWN))
        }
    }


    private suspend inline fun <inT, outT> mapData(
        data: inT?,
        code: Int,
        crossinline mapDelegate: suspend (inT) -> outT
    ): State<outT> {
        return if (data == null || data is Unit || data is Collection<*> && data.isEmpty()) {
            State.Failure(R.string.error_no_data, NoDataException(), ErrorCodes.STATE_NO_DATA)
        } else {
            try {
                State.Success(mapDelegate(data))
            } catch (e: Exception) {
                State.Failure(R.string.error_corrupted_data, e, code)
            }
        }
    }

    private suspend inline fun <T> handleState(
        state: State<T>,
        crossinline onSuccess: suspend (T) -> Unit,
        crossinline onError: suspend (Int, Throwable, Int) -> Unit,
    ) {
        when (state) {
            is State.Success                   -> {
                onSuccess(state.data!!)
            }
            is State.Failure                   -> {
                onError(state.messageID!!, state.throwable!!, state.errorCode!!)
            }
            is State.Loading, is State.Initial -> {
                //no-op
            }
        }
    }


    /**
     * - [localDataProvider] must be either [DataSource.Local] or [DataSource.LocalFlow].
     * Otherwise an [IllegalArgumentException] will be thrown.
     *
     * - [remoteDataProvider] must be either [DataSource.Remote] or [DataSource.Empty].
     * Otherwise an [IllegalArgumentException] will be thrown.
     *
     * - Screens that uses data from [State.Loading] to initialize UI should handle situations when
     * [State.Failure] will be emitted after [State.Loading] (e.g. ignore error, invalidate UI and show hard
     * message, show soft message).
     *
     * - [State.Failure] will never be emitted if [State.Success] was emitted at least once. But if local
     * data changes expected outside of this function (e.g. user changes search filter configuration),
     * [State.Failure] may be emitted even if [State.Success] was previously emitted.
     *
     * - If an error occurred both in local and remote data source, remote error will be emitted.
     *
     * - If a potential update is expected, data will always be emitted with [State.Loading].
     * Otherwise data will be emitted with [State.Success]
     *
     * - If [localDataProvider] was provided as [DataSource.LocalFlow], it must pass data after each
     * successful completion of [saveRemoteDelegate] even if data hasn't changed, so [getData] can emit data
     * into correct State (e.g. if local data was emitted with [State.Loading] and after [saveRemoteDelegate]
     * completion [localDataProvider] will not provide any data due to no changes was made in local DB.
     * In that case [State.Loading] will be the last emitted value, which can be bad for screens
     * that does not use data from [State.Loading]).
     *
     * - If data collected from local or remote flow is **Unit, null or empty collection**,
     * [State.Failure] will be emitted with [NoDataException].
     *
     * - Always use [DataSource.LocalFlow] inside [localDataProvider] with [remoteDataProvider], otherwise
     * new data after [saveRemoteDelegate] will not be received
     *
     * ### USE CASES:
     *
     * [getData] can be used to fetch only local data or without any mapping (but it still useful to handle
     * errors that may occur while fetching data).
     * In this case, [remoteT] == [Unit], and [localOutT] == [modelT]:
     * ~~~
     * fun getSomeData(): Flow<State<SomeData>> {
     *     return getData(
     *         remoteDataProvider = { DataProvider.Empty },
     *         localDataProvider = { DataProvider.LocalFlow(dao.fetch()) },
     *         saveRemoteDelegate = {},
     *         mapToLocalDelegate = {},
     *         mapToModelDelegate = { it }
     *     )
     * }
     * ~~~
     *
     * Also it is possible to preprocess data from [localDataProvider]. Be careful and avoid cases when
     * data was invalidated and not be followed by any changes in local data source. It may lead to infinite
     * loop or endless loading state for UI
     * ~~~
     * fun getSomeData(): Flow<State<SomeData>> {
     *     return getData(
     *         remoteDataProvider = { DataProvider.Remote(api.fetch()) },
     *         localDataFlowProvider = {
     *             DataProvider.LocalFLow(
     *                 dao.fetch().transform { data ->
     *                     // In this case, UI will receive only valid data.
     *                     val dataToUpdate = verifyData(data)
     *                     if (dataToUpdate != null) {
     *                         dao.update(dataToUpdate)
     *                     } else {
     *                         emit(data)
     *                     }
     *                 }
     *             )
     *         },
     *         saveRemoteDelegate = { dao.update(it) },
     *         mapToLocalDelegate = { it.toDB() },
     *         mapToModelDelegate = { it.toModel() }
     *     )
     * }
     * ~~~
     */
    internal inline fun <modelT, localInT, localOutT, remoteT> getData(
        crossinline localDataProvider: suspend () -> DataSource<localOutT>,
        crossinline remoteDataProvider: suspend () -> DataSource<remoteT>,
        crossinline saveRemoteDelegate: suspend (localInT) -> Unit,
        crossinline mapToLocalDelegate: (remoteT) -> localInT,
        crossinline mapToModelDelegate: (localOutT) -> modelT,
    ) = flow<State<modelT>> {
        emit(State.Initial())

        var remoteDataSaved = false
        var remoteSaveError: Exception? = null

        combine(
            fetchLocal(localDataProvider, mapToModelDelegate),
            fetchRemote(remoteDataProvider, mapToLocalDelegate)
        ) { local, remote ->
            when (remote) {
                is State.Loading, is State.Initial -> {
                    handleState(
                        state = local,
                        onSuccess = { localData ->
                            emit(State.Loading(localData))
                        },
                        onError = { _, _, _ ->
                            // ignore errors, wait until remote data fetched
                        }
                    )
                }
                is State.Success                   -> {
                    if (!remoteDataSaved) { // to prevent endless updateLocalDelegate calls
                        try {
                            saveRemoteDelegate(remote.data!!)
                        } catch (e: Exception) {
                            remoteSaveError = e
                        }
                    }

                    handleState(
                        state = local,
                        onSuccess = { localData ->
                            // emit loading only if new collection is expected
                            if (!remoteDataSaved && remoteSaveError == null) {
                                emit(State.Loading(localData))
                            } else {
                                emit(State.Success(localData))
                            }
                        },
                        onError = { messageID, error, errorCode ->
                            // emit error only if no new collection is expected
                            if (remoteSaveError != null) {
                                emit(
                                    State.Failure(
                                        R.string.error_unknown,
                                        remoteSaveError!!,
                                        ErrorCodes.STATE_REMOTE_SAVE
                                    )
                                )
                            } else if (remoteDataSaved) {
                                emit(State.Failure(messageID, error, errorCode))
                            }
                        }
                    )

                    // must be called AFTER handleState block
                    remoteDataSaved = true
                }
                is State.Failure                   -> {
                    handleState(
                        state = local,
                        onSuccess = { localData ->
                            emit(State.Success(localData))
                        },
                        onError = { _, _, _ ->
                            emit(State.Failure(remote.messageID!!, remote.throwable!!, remote.errorCode!!))
                        }
                    )
                }
            }
        }.collect {}
    }
}