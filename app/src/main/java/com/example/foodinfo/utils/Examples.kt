package com.example.foodinfo.utils

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


fun queryExample() {
    /*
        SELECT * FROM recipe WHERE
            total_time >= 40
            AND servings <= 4
            AND calories BETWEEN 1343 AND 1345
            AND id IN (SELECT nutrient_recipe_id FROM recipe_nutrients WHERE CASE
                    WHEN label = 'Fat' THEN total_value BETWEEN 16.0 AND 16.5
                    WHEN label = 'Carbs' THEN total_value <= 18.0
                    WHEN label = 'Protein' THEN total_value >= 20.0
                    ELSE NULL END
                GROUP BY nutrient_recipe_id
                HAVING  count(nutrient_recipe_id) = 3)
            AND id IN (SELECT label_recipe_id FROM recipe_labels WHERE CASE
                    WHEN category = 'cuisine' THEN label IN ('Japanese')
                    WHEN category = 'dish' THEN label IN ('omelet', 'egg')
                    WHEN category = 'meal' THEN label IN ('breakfast', 'dinner')
                    ELSE NULL END
                GROUP BY label_recipe_id
                HAVING  count(label_recipe_id) = 5)

   will return recipe with ID: 1MMVGIN7W58TZAXUI8C8
     */
}


// NOT YET TESTED
// this is just theoretically variant to implement data state handling
class RepositoryExample @Inject constructor(
    private val context: Context,
) {

    fun apiCall(): Any? {
        // fetch data from remote API
        return null
    }

    private fun fetchRemoteData(): Flow<State<Any>> {
        return flow {
            emit(State.Loading())
            if (context.hasInternet()) {
                try {
                    val data = apiCall()
                    if (data != null) {
                        emit(State.Success(data))
                    } else {
                        emit(State.Error("no data found", NoDataException()))
                    }
                } catch (e: Exception) {
                    emit(State.Error("something went wrong", e))
                }
            } else {
                emit(State.Error("no internet", NoInternetException()))
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun fetchLocalData(): Flow<Any?> {
        return flow<Any?> {
            // fetch data from local DB (e.g. dao.getData())
        }.flowOn(Dispatchers.IO)
    }

    fun getData(): Flow<State<Any>> {
        return flow<State<Any>> {
            var remoteEmitted = false
            emit(State.Loading()) // immediately emitting loading state
            combine(
                fetchLocalData(), // flow in DAO module
                fetchRemoteData() // flow in Remote module
            ) { local, remote ->
                when (remote) {
                    is State.Loading -> {
                        if (local != null) {
                            // mapping to Model and emitting local data if available while fetching data from remote
                            emit(State.Success(local))
                        }
                    }
                    is State.Success -> {
                        if (!remoteEmitted) { // if remote data not yet added into local DB

                            // map remote data into local and add it to local DB
                            // (e.g. dao.updateData(data.toEntity()))

                            // adding remote data into local DB may trigger combine block to collect data
                            // (if remote and local data are different) so this flag will determine
                            // is local data was already updated and should be emitted or not
                            remoteEmitted = true
                        } else { // assume that there is local data if reached this block
                            // mapping to Model and emitting local data
                            emit(State.Success(local!!))
                        }
                    }
                    is State.Error   -> {
                        if (local != null) {
                            // mapping to Model and emitting local data if available without errors
                            emit(State.Success(local))
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
}