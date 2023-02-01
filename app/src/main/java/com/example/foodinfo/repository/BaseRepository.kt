package com.example.foodinfo.repository

import android.content.Context
import com.example.foodinfo.utils.NoDataException
import com.example.foodinfo.utils.NoInternetException
import com.example.foodinfo.utils.State
import com.example.foodinfo.utils.hasInternet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


abstract class BaseRepository {

    private fun <T> fetchLocal(runnable: () -> Flow<T>): Flow<State<T>> {
        return flow<State<T>> {
            emit(State.Loading())
            try {
                runnable.invoke().collect { data ->
                    if (data == null || data is Collection<*> && data.isEmpty()) {
                        emit(State.Error("no data found", NoDataException()))
                    } else {
                        emit(State.Success(data))
                    }
                }
            } catch (e: Exception) {
                emit(State.Error("something went wrong", e))
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun <T> fetchRemote(context: Context, runnable: () -> T?): Flow<State<T>> {
        return flow<State<T>> {
            emit(State.Loading())
            if (context.hasInternet()) {
                try {
                    val data = runnable.invoke()
                    if (data == null || data is Collection<*> && data.isEmpty()) {
                        emit(State.Error("no data found", NoDataException()))
                    } else {
                        emit(State.Success(data))
                    }
                } catch (e: Exception) {
                    emit(State.Error("something went wrong", e))
                }
            } else {
                emit(State.Error("no internet", NoInternetException()))
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
                        if (local is State.Success) {

                            // mapping to Model and emitting local data if available while fetching data from remote
                            emit(State.Success(mapLocalToModelDelegate(local.data)))
                        }
                    }
                    is State.Success -> {
                        if (!remoteEmitted) { // if remote data not yet added into local DB

                            // map remote data into local and add it to local DB
                            updateLocalDelegate(mapRemoteToLocalDelegate(remote.data))

                            // adding remote data into local DB may trigger combine block to collect data
                            // (if remote and local data are different) so this flag will determine
                            // is local data was already updated and should be emitted or not
                            remoteEmitted = true

                        } else {
                            when (local) {
                                is State.Success -> {

                                    // mapping to Model and emitting local data
                                    emit(State.Success(mapLocalToModelDelegate(local.data)))
                                }
                                is State.Error   -> {

                                    // if failed to emit remote data into DB
                                    // or failed to fetch local data due to any reason (e.g. invalid query)
                                    emit(State.Error(local.message, local.error))
                                }
                                is State.Loading -> {} // do nothing, just wait until Success or Error
                            }
                        }
                    }
                    is State.Error   -> {
                        if (local is State.Success) {

                            // mapping to Model and emitting local data if available without errors
                            emit(State.Success(mapLocalToModelDelegate(local.data)))
                        } else {

                            // emitting error message from remote to display proper screen for user
                            // e.g. "No data found", "API is not responding" or "No internet connection"
                            emit(State.Error(remote.message, remote.error))
                        }
                    }
                }
            }.collect { }
        }.flowOn(Dispatchers.IO)
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