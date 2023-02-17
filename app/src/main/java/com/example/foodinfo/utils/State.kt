package com.example.foodinfo.utils


sealed class State<T>(
    val data: T? = null,
    val message: String? = null,
    val error: Exception? = null
) {
    class Loading<T>(data: T? = null) : State<T>(data)
    class Success<T>(data: T) : State<T>(data)
    class Error<T>(message: String, error: Exception) : State<T>(message = message, error = error)


    companion object Utils {

        /**
         * - If both states are [Error], it will compare it's [message] and [error] types.
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
                old is Error && new is Error                  -> {
                    old.message == new.message && old.error?.javaClass == new.error?.javaClass
                }
                (old is Loading && new is Loading)
                        || (old is Success && new is Success)
                        || (old is Success && new is Loading) -> {
                    isEqualData(old.data, new.data)
                }
                else                                          -> {
                    old.javaClass == new.javaClass
                }
            }
        }

        fun <T> isEmptyLoading(state: State<T>): Boolean {
            return state is Loading && state.data == null
        }

        private fun <T> isEqualData(data1: T?, data2: T?): Boolean {
            return if (data1 != null && data2 != null) {
                if (data1 is Collection<*> && data2 is Collection<*>) {
                    data1.toSet() == data2.toSet()
                } else {
                    data1.toString() == data2.toString()
                }
            } else data1 == null && data2 == null
        }
    }
}