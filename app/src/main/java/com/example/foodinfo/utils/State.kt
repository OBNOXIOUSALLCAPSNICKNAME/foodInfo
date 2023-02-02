package com.example.foodinfo.utils


sealed class State<T> {
    class Loading<T>(val data: T? = null) : State<T>()
    class Success<T>(val data: T) : State<T>()
    class Error<T>(val message: String, val error: Exception) : State<T>()

    fun equalState(other: State<T>): Boolean {
        return when {
            this is Error && other is Error     -> {
                this.message == other.message && this.error.javaClass == other.error.javaClass
            }
            this is Success && other is Success -> {
                this.data == other.data
            }
            else                                -> {
                this.javaClass == other.javaClass
            }
        }
    }
}