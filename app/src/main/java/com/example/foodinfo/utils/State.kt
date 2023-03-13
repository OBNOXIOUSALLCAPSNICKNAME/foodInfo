package com.example.foodinfo.utils


sealed class State<T>(
    val data: T? = null,
    val error: Throwable? = null,
    val errorCode: Int? = null,
    val messageID: Int? = null
) {
    class Loading<T>(data: T? = null) : State<T>(data)
    class Success<T>(data: T) : State<T>(data)
    class Error<T>(messageID: Int, error: Throwable, errorCode: Int = -1) : State<T>(
        messageID = messageID, errorCode = errorCode, error = error
    )


    companion object Utils {

        /**
         * - If both states are [Error], it will compare it's [messageID], [errorCode] and [error] types.
         * - If both states are [Loading] or both are [Success], it will compare it's [data].
         * - If [old] is [Success] and [new] is [Loading], it will compare it's [data] too. It helps for
         * screens that use data from [Loading] to avoid redundant UI updates because in most cases if UI
         * already received data with state [Success], next data in [Loading] will be the same.
         * - Otherwise it will compare state types
         *
         * If both [data] are [Collection], it will be converted into [Set] to ignore items order.
         */
        fun <T> isEqual(old: State<T>, new: State<T>): Boolean {
            return when {
                old is Error && new is Error       -> {
                    old.errorCode == new.errorCode &&
                    old.messageID == new.messageID &&
                    old.error?.javaClass == new.error?.javaClass
                }
                (old is Loading && new is Loading) ||
                (old is Success && new is Success) ||
                (old is Success && new is Loading) -> {
                    isEqualData(old.data, new.data)
                }
                else                               -> {
                    old.javaClass == new.javaClass
                }
            }
        }

        /**
         * - If both states are [Error], it will compare it's [messageID], [errorCode] and [error] types.
         * - If both states are [Loading] or [Success], it will compare it's [data].
         *
         * If both [data] are [Collection], it will be converted into [Set] to ignore items order.
         */
        fun <T> isEqualInsensitive(old: State<T>, new: State<T>): Boolean {
            return when {
                old is Error && new is Error       -> {
                    old.errorCode == new.errorCode &&
                    old.messageID == new.messageID &&
                    old.error?.javaClass == new.error?.javaClass
                }
                (old is Loading || old is Success) &&
                (new is Loading || new is Success) -> {
                    isEqualData(old.data, new.data)
                }
                else                               -> {
                    old.javaClass == new.javaClass
                }
            }
        }

        fun <T> isEmptyLoading(state: State<T>): Boolean {
            return state is Loading && state.data == null
        }

        fun <T> isEqualData(old: T?, new: T?): Boolean {
            return if (old != null && new != null) {
                when {
                    old is Collection<*> && new is Collection<*>   -> {
                        old.size == new.size && old.toSet() == new.toSet()
                    }
                    old !is Collection<*> && new !is Collection<*> -> {
                        old.toString() == new.toString()
                    }
                    else                                           -> false
                }
            } else false
        }
    }
}