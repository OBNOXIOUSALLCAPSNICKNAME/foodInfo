package com.example.foodinfo.repository.state_handling


sealed class State<T>(
    val data: T? = null,
    val throwable: Throwable? = null,
    val errorCode: Int? = null,
    val messageID: Int? = null
) {
    class Loading<T>(data: T? = null) : State<T>(data)
    class Success<T>(data: T) : State<T>(data)
    class Error<T>(messageID: Int, throwable: Throwable, errorCode: Int = -1) : State<T>(
        messageID = messageID, errorCode = errorCode, throwable = throwable
    )


    companion object Utils {
        /**
         * - If both states are [Error], it will compare them using [isEqualError].
         * - If both states are [Loading] or both are [Success], it will compare it's [data] using [isEqualData].
         * - If [old] is [Success] and [new] is [Loading], it will compare it's [data] too. It helps for
         * screens that use data from [Loading] to avoid redundant UI updates because in most cases if UI
         * already received data with state [Success], next data in [Loading] will be the same.
         */
        fun <T> isEqual(old: State<T>, new: State<T>): Boolean {
            return when {
                old is Error && new is Error       -> {
                    isEqualError(old, new)
                }
                (old is Loading && new is Loading) ||
                (old is Success && new is Success) ||
                (old is Success && new is Loading) -> {
                    isEqualData(old.data, new.data)
                }
                else                               -> false
            }
        }

        /**
         * - If both states are [Error], it will compare them using [isEqualError].
         * - If both states are [Loading] or [Success], it will compare it's [data] using [isEqualData].
         */
        fun <T> isEqualInsensitive(old: State<T>, new: State<T>): Boolean {
            return when {
                old is Error && new is Error       -> {
                    isEqualError(old, new)
                }
                (old is Loading || old is Success) &&
                (new is Loading || new is Success) -> {
                    isEqualData(old.data, new.data)
                }
                else                               -> false
            }
        }

        /**
         * Used to filter out [Loading] with [data] **== null** from any data flow.
         *
         * It may help to handle cases when [isEqual] or [isEqualInsensitive] will not be able to filter out
         * repetitions of the same state due to data flow emits as follows:
         * ~~~
         * Success("a_1")
         * Loading(null)
         * Loading("a_1")
         * Success("a_1")
         * ~~~
         */
        fun <T> isEmptyLoading(state: State<T>): Boolean {
            return state is Loading && state.data == null
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
         * Compare [Error] by it's [errorCode], [messageID], [throwable] type and [throwable] message.
         */
        private fun <T> isEqualError(old: Error<T>, new: Error<T>) =
            old.errorCode == new.errorCode &&
            old.messageID == new.messageID &&
            old.throwable?.message == new.throwable?.message &&
            old.throwable?.javaClass == new.throwable?.javaClass

    }
}