package com.example.foodinfo.repository

import android.content.Context
import com.example.foodinfo.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*


abstract class BaseRepository {

    private fun <T> fetchRemote(context: Context, fetchDelegate: () -> T?): Flow<State<T>> {
        return flow<State<T>> {
            emit(State.Loading())
            try {
                if (context.hasInternet()) {
                    emitData(fetchDelegate(), this::emit)
                } else {
                    emit(State.Error(ErrorMessages.NO_INTERNET, NoInternetException()))
                }
            } catch (e: Exception) {
                emit(State.Error(ErrorMessages.UNKNOWN_ERROR, e))
            }

        }.flowOn(Dispatchers.IO)
    }

    private fun <T> fetchLocal(
        fetchOnceDelegate: (() -> T)? = null,
        fetchFlowDelegate: (() -> Flow<T>)? = null,
    ): Flow<State<T>> {
        if (fetchOnceDelegate == null && fetchFlowDelegate == null)
            throw java.lang.NullPointerException()

        return flow<State<T>> {
            emit(State.Loading())
            try {
                if (fetchOnceDelegate != null) {
                    emitData(fetchOnceDelegate(), this::emit)
                } else {
                    fetchFlowDelegate!!().distinctUntilChanged().collect { data ->
                        emitData(data, this::emit)
                    }
                }
            } catch (e: Exception) {
                emit(State.Error(ErrorMessages.UNKNOWN_ERROR, e))
            }
        }.flowOn(Dispatchers.IO)
    }


    protected fun <modelT, localInT, localOutT, remoteT> getLatest(
        context: Context,
        fetchRemoteDelegate: () -> remoteT,
        fetchLocalOnceDelegate: (() -> localOutT)? = null,
        fetchLocalFlowDelegate: (() -> Flow<localOutT>)? = null,
        updateLocalDelegate: (localInT) -> Unit,
        mapRemoteToLocalDelegate: (remoteT) -> localInT,
        mapLocalToModelDelegate: (localOutT) -> modelT,
    ): Flow<State<modelT>> {
        return flow<State<modelT>> {
            var remoteEmitted = false
            var remoteEmissionError: Exception? = null
            emit(State.Loading()) // immediately emitting loading state
            combine(
                fetchLocal(fetchLocalOnceDelegate, fetchLocalFlowDelegate), // flow of data from local source
                fetchRemote(context, fetchRemoteDelegate) // flow of data from remote source
            ) { local, remote ->
                when (remote) {
                    is State.Loading -> {

                        // try map to Model and emit local data. Ignore errors, wait until remote data fetched
                        tryEmitState(local, mapLocalToModelDelegate, this::emit)
                    }
                    is State.Success -> {
                        if (!remoteEmitted) { // if remote data not yet added into local DB
                            try {

                                // try map remote data into local and add it to local DB
                                updateLocalDelegate(mapRemoteToLocalDelegate(remote.data))
                            } catch (e: Exception) {

                                // save remote emission error
                                remoteEmissionError = e
                            } finally {

                                // adding remote data into local DB may trigger combine block to collect data
                                // (if remote and local data are different) so this flag will determine
                                // if local data was already updated and should be emitted or not
                                // to prevent updateLocalDelegate to trigger twice
                                remoteEmitted = true
                            }
                        }

                        tryEmitState(local, mapLocalToModelDelegate, this::emit)?.let { error ->

                            // emit error only when remote data was already added unto DB, else just wait
                            when {
                                remoteEmitted && remoteEmissionError != null -> {
                                    emit(State.Error(ErrorMessages.UNKNOWN_ERROR, remoteEmissionError!!))
                                }
                                remoteEmitted && remoteEmissionError == null -> {
                                    emit(State.Error(error.message, error.error))
                                }
                            }
                        }
                    }
                    is State.Error   -> {
                        tryEmitState(local, mapLocalToModelDelegate, this::emit)?.let {
                            emit(State.Error(remote.message, remote.error))
                        }
                    }
                }
            }.collect { }
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun <localT, modelT> tryEmitState(
        local: State<localT>,
        mapDelegate: (localT) -> modelT,
        emit: suspend (State<modelT>) -> Unit,
    ): State.Error<modelT>? {
        return when (local) {
            is State.Success -> {
                try {
                    emit(State.Success(mapDelegate(local.data)))
                    null
                } catch (e: Exception) {

                    // if local data was successfully fetched but mapping failed, it means that
                    // local data does not fit the current model (for example, if some data was partially
                    // loaded on previous screen but it is not enough for current case)
                    State.Error(ErrorMessages.CORRUPTED_DATA, CorruptedDataException())
                }
            }
            is State.Error   -> {
                State.Error(local.message, local.error)
            }
            is State.Loading -> {
                null
            }
        }
    }

    private suspend fun <T> emitData(data: T?, emit: suspend (State<T>) -> Unit) {
        if (data == null || data is Collection<*> && data.isEmpty()) {
            emit(State.Error(ErrorMessages.NO_DATA, NoDataException()))
        } else {
            emit(State.Success(data))
        }
    }
}