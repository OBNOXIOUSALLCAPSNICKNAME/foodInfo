package com.example.foodinfo.utils


class NoDataException(message: String = ErrorMessages.NO_DATA) : Exception(message)

class NoInternetException(message: String = ErrorMessages.NO_INTERNET) : Exception(message)

class CorruptedDataException(message: String = ErrorMessages.CORRUPTED_DATA) : Exception(message)


object ErrorMessages {
    const val NO_DATA = "No data found. Please change the search text or filter settings and try again"
    const val NO_INTERNET = "Please connect to the internet and try again"
    const val UNKNOWN_ERROR = "Something went wrong. Please try again"
    const val CORRUPTED_DATA = "Data is corrupted. Please connect to the internet and try again"
}