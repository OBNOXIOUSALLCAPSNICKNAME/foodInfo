package com.example.foodinfo.utils


sealed class UIState {
    class Loading : UIState()
    class Success : UIState()
    class Error(val message: String, val error: Exception) : UIState()

    fun equalState(other: Any): Boolean {
        if (other is Error && this is Error) {
            return this.message == other.message &&
                    this.error.javaClass == other.error.javaClass
        }
        return this.javaClass == other.javaClass
    }
}