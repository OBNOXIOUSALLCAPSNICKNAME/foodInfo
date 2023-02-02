package com.example.foodinfo.repository

import android.content.Context
import com.example.foodinfo.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*


abstract class BaseRepository {

    private fun <T> fetchLocal(fetchDelegate: () -> Flow<T>): Flow<State<T>> {
        return flow<State<T>> {
            emit(State.Loading())
            try {
                fetchDelegate.invoke().distinctUntilChanged().collectLatest { data ->
                    if (data == null || data is Collection<*> && data.isEmpty()) {
                        emit(State.Error(ErrorMessages.NO_DATA, NoDataException()))
                    } else {
                        emit(State.Success(data))
                    }
                }
            } catch (e: Exception) {
                emit(State.Error(ErrorMessages.UNKNOWN_ERROR, e))
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun <T> fetchRemote(context: Context, fetchDelegate: () -> T?): Flow<State<T>> {
        return flow<State<T>> {
            emit(State.Loading())
            if (context.hasInternet()) {
                try {
                    val data = fetchDelegate.invoke()
                    if (data == null || data is Collection<*> && data.isEmpty()) {
                        emit(State.Error(ErrorMessages.NO_DATA, NoDataException()))
                    } else {
                        emit(State.Success(data))
                    }
                } catch (e: Exception) {
                    emit(State.Error(ErrorMessages.UNKNOWN_ERROR, e))
                }
            } else {
                emit(State.Error(ErrorMessages.NO_INTERNET, NoInternetException()))
            }
        }.flowOn(Dispatchers.IO)
    }


    fun <modelT, localT, remoteT> getLatest(
        // not sure if "Delegate" postfix is correct here
        context: Context,
        fetchLocalDelegate: () -> Flow<localT>,
        fetchRemoteDelegate: () -> remoteT,
        updateLocalDelegate: (localT) -> Unit,
        mapRemoteToLocalDelegate: (remoteT) -> localT,
        mapLocalToModelDelegate: (localT) -> modelT,
    ): Flow<State<modelT>> {
        return flow<State<modelT>> {
            var remoteEmitted = false
            emit(State.Loading()) // immediately emitting loading state
            combine(
                fetchLocal(fetchLocalDelegate), // flow in Local module
                fetchRemote(context, fetchRemoteDelegate) // flow in Remote module
            ) { local, remote ->
                when (remote) {
                    is State.Loading -> {

                        // try map to Model and emit local data. Ignore errors, wait until remote data fetched
                        tryEmit(local, mapLocalToModelDelegate, this::emit)
                    }
                    is State.Success -> {
                        if (!remoteEmitted) { // if remote data not yet added into local DB
                            try {

                                // try map remote data into local and add it to local DB
                                updateLocalDelegate(mapRemoteToLocalDelegate(remote.data))
                            } catch (e: Exception) {

                                // last chance to load any data
                                tryEmit(local, mapLocalToModelDelegate, this::emit)?.let {
                                    emit(State.Error(ErrorMessages.UNKNOWN_ERROR, e))
                                }
                            } finally {

                                // adding remote data into local DB may trigger combine block to collect data
                                // (if remote and local data are different) so this flag will determine
                                // was local data already updated and should be emitted or not
                                remoteEmitted = true
                            }
                        } else { // when remote data was successfully added into local DB

                            tryEmit(local, mapLocalToModelDelegate, this::emit)?.let { error ->
                                emit(State.Error(error.message, error.error))
                            }
                        }
                    }
                    is State.Error   -> {

                        tryEmit(local, mapLocalToModelDelegate, this::emit)?.let {
                            emit(State.Error(remote.message, remote.error))
                        }
                    }
                }
            }.collectLatest { }
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun <localT, modelT> tryEmit(
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

    /* Use case:

        fun getRecipeExtended(ID: String): Flow<State<RecipeExtendedModel>> {
            return getLatest(
                context = context,
                fetchLocalDelegate = { recipeDAO.getByIdExtended(ID) },
                fetchRemoteDelegate = { recipeAPI.getRecipe(ID) },
                updateLocalDelegate = { recipeDAO.addRecipe(it) },
                mapRemoteToLocalDelegate = { it.toDB() },
                mapLocalToModelDelegate = { it.toModelExtended() }
            )
        }

     */
}