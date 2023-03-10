package com.example.foodinfo.repository

import android.util.Log
import com.example.foodinfo.R
import com.example.foodinfo.remote.response.ApiResponse
import com.example.foodinfo.remote.response.NetworkResponse
import com.example.foodinfo.utils.NoDataException
import com.example.foodinfo.utils.State
import com.example.foodinfo.utils.trimMultiline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


abstract class BaseRepository {

    /**
     * Wrapper interface that helps [getData] to properly handle different data sources
     */
    internal sealed interface DataProvider<T> {
        data class Remote<T : Any>(val response: ApiResponse<T>) : DataProvider<T>
        data class Local<T>(val data: T) : DataProvider<T>
        data class LocalFlow<T>(val flow: Flow<T>) : DataProvider<T>
        object Empty : DataProvider<Unit>
    }


    private inline fun <remoteT, localInT> fetchRemote(
        crossinline dataProvider: suspend () -> DataProvider<remoteT>,
        crossinline mapDelegate: (remoteT) -> localInT
    ) = flow {
        emit(State.Loading())

        try {
            when (val provider = dataProvider()) {
                is DataProvider.Remote -> {
                    when (provider.response) {
                        is NetworkResponse.Success -> {
                            emit(mapData(provider.response.result, mapDelegate))
                        }
                        else                       -> {
                            emit(
                                State.Error(
                                    (provider.response as NetworkResponse.Error).messageID,
                                    (provider.response as NetworkResponse.Error).error,
                                    (provider.response as NetworkResponse.Error).code
                                )
                            )
                        }
                    }
                }
                is DataProvider.Empty  -> {
                    emit(State.Error(R.string.error_no_data, NoDataException()))
                }
                else                   -> {
                    throw IllegalArgumentException(
                        """
                            Unsupported remoteDataProvider type: ${provider::class.java}.
                            remoteDataProvider must be either 'DataProvider.Remote' or 'DataProvider.Empty'
                        """.trimMultiline()
                    )
                }
            }
        } catch (e: Exception) {
            emit(State.Error(R.string.error_unknown, e))
        }
    }.flowOn(Dispatchers.IO)

    private inline fun <localInT, modelT> fetchLocal(
        crossinline dataProvider: suspend () -> DataProvider<localInT>,
        crossinline mapDelegate: (localInT) -> modelT
    ) = flow {
        emit(State.Loading())

        try {
            when (val provider = dataProvider()) {
                is DataProvider.Local -> {
                    emit(mapData(provider.data, mapDelegate))
                }
                is DataProvider.LocalFlow -> {
                    provider.flow.collect { data ->
                        emit(mapData(data, mapDelegate))
                    }
                }
                else -> {
                    throw IllegalArgumentException(
                        """
                            Unsupported localDataProvider type: ${provider::class.java}.
                            localDataProvider must be either 'DataProvider.LocalFlow' or 'DataProvider.Local'
                        """.trimMultiline()
                    )
                }
            }
        } catch (e: Exception) {
            emit(State.Error(R.string.error_unknown, e))
        }
    }.flowOn(Dispatchers.IO)


    private suspend inline fun <inT, outT> mapData(
        data: inT?,
        crossinline mapDelegate: suspend (inT) -> outT
    ): State<outT> {
        return if (data == null || data is Unit || data is Collection<*> && data.isEmpty()) {
            State.Error(R.string.error_no_data, NoDataException())
        } else {
            try {
                State.Success(mapDelegate(data))
            } catch (e: Exception) {
                State.Error(R.string.error_corrupted_data, e)
            }
        }
    }

    private suspend inline fun <T> handleState(
        state: State<T>,
        crossinline onSuccess: suspend (T) -> Unit,
        crossinline onError: suspend (Int, Throwable) -> Unit,
    ) {
        when (state) {
            is State.Success -> {
                onSuccess(state.data!!)
            }
            is State.Error   -> {
                onError(state.messageID!!, state.error!!)
            }
            is State.Loading -> {}
        }
    }


    /**
     * ### NOTES:
     * - [localDataProvider] must be either [DataProvider.Local] or [DataProvider.LocalFlow].
     * Otherwise an [IllegalArgumentException] will be thrown.
     *
     * - [remoteDataProvider] must be either [DataProvider.Remote] or [DataProvider.Empty].
     * Otherwise an [IllegalArgumentException] will be thrown.
     *
     * - Screens that uses data from [State.Loading] to initialize UI should handle situations when
     * [State.Error] will be emitted after [State.Loading] with data != null (e.g. ignore error,
     * invalidate UI and show hard message, show soft message).
     *
     * - [State.Error] will never be emitted if [State.Success] was emitted at least once. But if local data
     * changes expected outside of this function (e.g. user changes search filter configuration),
     * [State.Error] may be emitted even if [State.Success] was previously emitted.
     *
     * - If an error occurred both in local and remote data source, remote error will be emitted.
     *
     * - If a potential update is expected, data will always be emitted with [State.Loading].
     * Otherwise data will be emitted with [State.Success]
     *
     * - If [localDataProvider] was provided as [DataProvider.LocalFlow], it must pass data after each successful
     * completion of [saveRemoteDelegate] even if data hasn't changed, so [getData] can emit data into
     * correct State (e.g. if local data was emitted with [State.Loading] and after [saveRemoteDelegate]
     * completion [localDataProvider] will not provide any data due to no changes was made in local DB.
     * In that case [State.Loading] will be the last emitted value, which can be bad for screens
     * that does not use data from [State.Loading]).
     *
     * - If data collected from local or remote flow is **Unit, null or empty collection**,
     * [State.Error] will be emitted with [NoDataException].
     *
     * - Always use [DataProvider.LocalFlow] inside [localDataProvider] with [remoteDataProvider], otherwise new data
     * after [saveRemoteDelegate] will not be received
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
     *         mapToModelDelegate = {}
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
        crossinline localDataProvider: suspend () -> DataProvider<localOutT>,
        crossinline remoteDataProvider: suspend () -> DataProvider<remoteT>,
        crossinline saveRemoteDelegate: (localInT) -> Unit,
        crossinline mapToLocalDelegate: (remoteT) -> localInT,
        crossinline mapToModelDelegate: (localOutT) -> modelT,
    ) = flow<State<modelT>> {
        emit(State.Loading())

        var remoteDataSaved = false
        var remoteSaveError: Exception? = null

        combine(
            fetchLocal(localDataProvider, mapToModelDelegate),
            fetchRemote(remoteDataProvider, mapToLocalDelegate)
        ) { local, remote ->
            when (remote) {
                is State.Loading -> {
                    handleState(
                        state = local,
                        onSuccess = { localData ->
                            emit(State.Loading(localData))
                        },
                        onError = { _, _ ->
                            // ignore errors, wait until remote data fetched
                        }
                    )
                }
                is State.Success -> {
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
                        onError = { messageID, error ->
                            // emit error only if no new collection is expected
                            if (remoteSaveError != null) {
                                emit(State.Error(R.string.error_unknown, remoteSaveError!!))
                            } else if (remoteDataSaved) {
                                emit(State.Error(messageID, error))
                            }
                        }
                    )

                    remoteDataSaved = true
                }
                is State.Error   -> {
                    Log.d("123", "error ${remote.error!!}, errorCode ${remote.errorCode!!}")
                    handleState(
                        state = local,
                        onSuccess = { localData ->
                            emit(State.Success(localData))
                        },
                        onError = { _, _ ->
                            emit(State.Error(remote.messageID!!, remote.error!!))
                        }
                    )
                }
            }
        }.collect {}
    }
}