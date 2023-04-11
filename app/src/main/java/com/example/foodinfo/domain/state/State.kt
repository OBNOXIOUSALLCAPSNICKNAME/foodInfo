package com.example.foodinfo.domain.state

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged


sealed class State<T>(
    val data: T? = null,
    val throwable: Throwable? = null,
    val errorCode: Int? = null,
    val messageID: Int? = null
) {
    /**
     * Means that loading has started. Must be emitted only once and immediately before any operations
     * to inform UI that loading has started as soon as possible.
     */
    class Initial<T> : State<T>()

    /**
     * Means that data was loaded successfully but it may be outdated and it's update expected. UI should
     * decide whether to use this data or wait until [Success] or [Error].
     */
    class Loading<T>(data: T) : State<T>(data)

    /**
     * Means that data was loaded successfully. This state is final meaning that there will be no subsequent
     * state emissions unless data storage manipulations is expected outside of the flow that
     * provided this state.
     */
    class Success<T>(data: T) : State<T>(data)

    /**
     * Means that an error occurred while trying to load data. Contains error itself,
     * short message that describes error and error code (e.g. **4xx** for client errors, **5xx** for server
     * and **8xx** for app-specific operations such as data mapping). This state is final meaning that there
     * will be no subsequent emissions of states unless data storage manipulations is expected outside of the
     * flow that provided this state.
     */
    class Failure<T>(messageID: Int, throwable: Throwable, errorCode: Int) : State<T>(
        messageID = messageID, errorCode = errorCode, throwable = throwable
    )


    companion object Utils {

        /**
         * @param useLoadingData If true - return flow where all subsequent repetitions of the same value
         * are filtered out with [isEqualInsensitive], otherwise with [isEqual].
         */
        fun <T> Flow<State<T>>.filterState(useLoadingData: Boolean): Flow<State<T>> {
            return if (useLoadingData) {
                this.distinctUntilChanged(Utils::isEqualInsensitive)
            } else {
                this.distinctUntilChanged(Utils::isEqual)
            }
        }

        /**
         * - If both states are [Failure], it will compare them using [isEqualError].
         * - If both states are [Loading] or both are [Success], it will compare it's [data] using [isEqualData].
         * - If [old] is [Success] and [new] is [Loading], it will compare it's [data] too. It helps for
         * screens that use data from [Loading] to avoid redundant UI updates because in most cases if UI
         * already received data with state [Success], next data in [Loading] will be the same.
         */
        fun <T> isEqual(old: State<T>, new: State<T>): Boolean {
            return when {
                old is Failure && new is Failure   -> {
                    isEqualError(old, new)
                }
                (old is Loading && new is Loading) ||
                (old is Success && new is Success) ||
                (old is Success && new is Loading) -> {
                    isEqualData(old.data, new.data)
                }
                else                               -> {
                    old::class == new::class
                }
            }
        }

        /**
         * - If both states are [Failure], it will compare them using [isEqualError].
         * - If both states are [Loading] or [Success], it will compare it's [data] using [isEqualData].
         */
        fun <T> isEqualInsensitive(old: State<T>, new: State<T>): Boolean {
            return when {
                old is Failure && new is Failure   -> {
                    isEqualError(old, new)
                }
                (old is Loading || old is Success) &&
                (new is Loading || new is Success) -> {
                    isEqualData(old.data, new.data)
                }
                else                               -> {
                    old::class == new::class
                }
            }
        }

        /**
         * - If **T** is [Collection], data will be compared ignoring item's order.
         * - If **T** is not [Collection], data will be compared by [equals].
         *
         * Be careful using [isEqualData] without overriding [equals] as it may lead to unexpected behavior.
         * For example, removing and re-inserting same data into local DB with auto generated primary key
         * or having image URL with dynamic access token may lead to [isEqualData] returns false even if
         * logically data are same.
         */
        fun <T> isEqualData(old: T?, new: T?): Boolean {
            return if (old != null && new != null) {
                when {
                    old is Collection<*> && new is Collection<*>   -> {
                        old.size == new.size && old.toSet() == new.toSet()
                    }
                    old !is Collection<*> && new !is Collection<*> -> {
                        old == new
                    }
                    else                                           -> false
                }
            } else false
        }

        /**
         * Compare [Failure] by it's [errorCode], [messageID], [throwable] type and [throwable] message.
         */
        private fun <T> isEqualError(old: Failure<T>, new: Failure<T>) =
            old.errorCode == new.errorCode &&
            old.messageID == new.messageID &&
            old.throwable?.message == new.throwable?.message &&
            old.throwable?.javaClass == new.throwable?.javaClass

    }
}