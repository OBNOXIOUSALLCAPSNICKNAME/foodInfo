package com.example.foodinfo.utils


sealed class State<T> {
    class Loading<T>(val data: T? = null) : State<T>()
    data class Success<T>(val data: T) : State<T>()
    class Error<T>(val message: String, val error: Exception) : State<T>()
}