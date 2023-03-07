package com.example.foodinfo.remote.response

import java.io.IOException


sealed class Response<out S : Any, out E : Any> {

    data class Success<T : Any>(
        val result: T
    ) : Response<T, Nothing>()

    interface Error {
        val code: Int
        val error: Throwable?
        val messageID: Int
    }

    data class ClientError<T : Any, E : Any>(
        override val code: Int,
        val body: T? = null,
        val errorBody: E? = null
    ) : Response<T, E>(), Error {
        override val error = IOException("$errorBody")
        override val messageID: Int = -1
    }

    data class ServerError<E : Any>(
        override val code: Int,
        val errorBody: E? = null
    ) : Response<Nothing, E>(), Error {
        override val error = IOException("$errorBody")
        override val messageID: Int = -1
    }

    data class NetworkError(
        override val code: Int = -1,
        override val error: Throwable,
        override val messageID: Int = -1
    ) : Response<Nothing, Nothing>(), Error

    data class UnknownError(
        override val code: Int = -1,
        override val error: Throwable,
        override val messageID: Int = -1
    ) : Response<Nothing, Nothing>(), Error
}

typealias NetworkResponse<S> = Response<S, Response.Error>