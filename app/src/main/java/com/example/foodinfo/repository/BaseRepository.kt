package com.example.foodinfo.repository

import com.example.foodinfo.utils.ErrorMessages
import com.example.foodinfo.utils.NoDataException
import com.example.foodinfo.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


abstract class BaseRepository {

    private inline fun <remoteT, localInT> fetchRemote(
        crossinline dataProvider: () -> remoteT,
        crossinline mapDelegate: (remoteT) -> localInT
    ) = flow {
        emit(State.Loading())

        try {
            val response = dataProvider() // TODO extract data from response
            emitData(response, mapDelegate, this::emit)
        } catch (e: Exception) {
            emit(State.Error(ErrorMessages.UNKNOWN_ERROR, e))
        }
    }.flowOn(Dispatchers.IO)

    private inline fun <localInT, modelT> fetchLocal(
        noinline dataProvider: (() -> localInT)? = null,
        noinline dataFlowProvider: (() -> Flow<localInT>)? = null,
        crossinline mapDelegate: (localInT) -> modelT
    ) = flow {
        emit(State.Loading())

        if (dataProvider == null && dataFlowProvider == null)
            throw java.lang.IllegalArgumentException()

        try {
            if (dataProvider != null) {
                val data = dataProvider()
                emitData(data, mapDelegate, this::emit)
            } else {
                dataFlowProvider!!().collect { data ->
                    emitData(data, mapDelegate, this::emit)
                }
            }
        } catch (e: Exception) {
            emit(State.Error(ErrorMessages.UNKNOWN_ERROR, e))
        }
    }.flowOn(Dispatchers.IO)


    private suspend inline fun <inT, outT> emitData(
        data: inT?,
        crossinline mapDelegate: suspend (inT) -> outT,
        crossinline emit: suspend (State<outT>) -> Unit
    ) {
        if (data == null || data is Unit || data is Collection<*> && data.isEmpty()) {
            emit(State.Error(ErrorMessages.NO_DATA, NoDataException()))
        } else {
            try {
                emit(State.Success(mapDelegate(data)))
            } catch (e: Exception) {
                emit(State.Error(ErrorMessages.UNKNOWN_ERROR, e))
            }
        }
    }

    private suspend inline fun <T> handleState(
        state: State<T>,
        crossinline onSuccess: suspend (T) -> Unit,
        crossinline onError: suspend (String, Exception) -> Unit,
    ) {
        when (state) {
            is State.Success -> {
                onSuccess(state.data!!)
            }
            is State.Error   -> {
                onError(state.message!!, state.error!!)
            }
            is State.Loading -> {}
        }
    }


    /**
     * ### NOTES:
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
     * - Local DB must pass data into [localDataFlowProvider] after each successful completion of
     * [updateLocalDelegate] even if data hasn't changed, so [getData] can emit data into correct State
     * (e.g. if local data was emitted with [State.Loading] and after [updateLocalDelegate] completion
     * [localDataFlowProvider] will not provide any data due to no changes was made in local DB.
     * In that case [State.Loading] will be the last emitted value, which can be bad for screens
     * that does not use data from [State.Loading]).
     *
     * - If local or remote data source will pass null or empty collection, [getData] will emit [State.Error]
     * with [NoDataException]
     *
     * - [localDataFlowProvider] and [localDataProvider] are optional, but at least one must be provided,
     * otherwise an [IllegalArgumentException] will be thrown.
     *
     * - Always use [localDataFlowProvider] with [remoteDataProvider], otherwise new data
     * after [updateLocalDelegate] will not be received
     *
     * ### USE CASES:
     *
     * [getData] can be used to fetch only local data or without any mapping (but it still useful to handle
     * errors that may occur while fetching data).
     * In this case, [remoteT] == [Unit], and [localOutT] == [modelT]:
     * ~~~
     * fun getSomeData(): Flow<State<SomeData>> {
     *     return getData(
     *         remoteDataProvider = {},
     *         localDataProvider = { dao.fetch() },
     *         updateLocalDelegate = {},
     *         mapToLocalDelegate = {},
     *         mapToModelDelegate = {}
     *     )
     * }
     * ~~~
     *
     * Also it is possible to preprocess data from [localDataFlowProvider]. Be careful and avoid cases when
     * data was invalidated and not be followed by any changes in local data source. It may lead to infinite
     * loop or endless loading state for UI
     * ~~~
     * fun getSomeData(): Flow<State<SomeData>> {
     *     return getData(
     *         remoteDataProvider = { api.fetch() },
     *         localDataFlowProvider = {
     *             dao.fetch().transform { data ->
     *                 // In this case, UI will receive only valid data.
     *                 val dataToUpdate = verifyData(data)
     *                 if (dataToUpdate != null) {
     *                     dao.update(dataToUpdate)
     *                 } else {
     *                     emit(data)
     *                 }
     *             }
     *         },
     *         updateLocalDelegate = { dao.update(it) },
     *         mapToLocalDelegate = { it.toDB() },
     *         mapToModelDelegate = { it.toModel() }
     *     )
     * }
     * ~~~
     */
    internal inline fun <modelT, localInT, localOutT, remoteT> getData(
        crossinline remoteDataProvider: () -> remoteT,
        noinline localDataProvider: (() -> localOutT)? = null,
        noinline localDataFlowProvider: (() -> Flow<localOutT>)? = null,
        crossinline updateLocalDelegate: (localInT) -> Unit,
        crossinline mapToLocalDelegate: (remoteT) -> localInT,
        crossinline mapToModelDelegate: (localOutT) -> modelT,
    ) = flow<State<modelT>> {
        emit(State.Loading())

        var localDBUpdated = false
        var localDBUpdateError: Exception? = null

        combine(
            fetchLocal(localDataProvider, localDataFlowProvider, mapToModelDelegate),
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
                    if (!localDBUpdated) { // to prevent endless updateLocalDelegate calls
                        try {
                            updateLocalDelegate(remote.data!!)
                        } catch (e: Exception) {
                            localDBUpdateError = e
                        }
                    }

                    handleState(
                        state = local,
                        onSuccess = { localData ->
                            // emit loading only if new collection is expected
                            if (!localDBUpdated && localDBUpdateError == null) {
                                emit(State.Loading(localData))
                            } else {
                                emit(State.Success(localData))
                            }
                        },
                        onError = { message, error ->
                            // emit error only if no new collection is expected
                            if (localDBUpdateError != null) {
                                emit(State.Error(ErrorMessages.UNKNOWN_ERROR, localDBUpdateError!!))
                            } else if (localDBUpdated) {
                                emit(State.Error(message, error))
                            }
                        }
                    )

                    localDBUpdated = true
                }
                is State.Error   -> {
                    handleState(
                        state = local,
                        onSuccess = { localData ->
                            emit(State.Success(localData))
                        },
                        onError = { _, _ ->
                            emit(State.Error(remote.message!!, remote.error!!))
                        }
                    )
                }
            }
        }.collect { }
    }.flowOn(Dispatchers.IO)
}