package com.example.foodinfo.remote.response

import com.example.foodinfo.R
import com.example.foodinfo.utils.NoInternetException
import com.example.foodinfo.utils.UnknownException
import okhttp3.ResponseBody
import java.io.IOException


internal const val NETWORK_ERROR_CODE = -1
internal const val UNKNOWN_ERROR_CODE = -1
internal val CLIENT_ERROR_RANGE = 400..499
internal val SERVER_ERROR_RANGE = 500..599

sealed class NetworkResponse<out S : Any, out E : Any> {

    data class Success<T : Any>(
        val result: T
    ) : NetworkResponse<T, Nothing>()

    interface Error {
        val code: Int
        val error: Throwable
        val messageID: Int
    }

    data class ClientError<T : Any, E : Any>(
        override val code: Int,
        val body: T? = null,
        val errorBody: E? = null
    ) : NetworkResponse<T, E>(), Error {
        override val error = IOException("$errorBody")
        override val messageID: Int = R.string.error_client
    }

    data class ServerError<E : Any>(
        override val code: Int,
        val errorBody: E? = null
    ) : NetworkResponse<Nothing, E>(), Error {
        override val error = IOException("$errorBody")
        override val messageID: Int = R.string.error_server
    }

    data class NetworkError(
        override val code: Int = NETWORK_ERROR_CODE,
        override val error: Throwable = NoInternetException(),
        override val messageID: Int = R.string.error_no_internet
    ) : NetworkResponse<Nothing, Nothing>(), Error

    data class UnknownError(
        override val code: Int = UNKNOWN_ERROR_CODE,
        override val error: Throwable = UnknownException(),
        override val messageID: Int = R.string.error_unknown
    ) : NetworkResponse<Nothing, Nothing>(), Error
}

typealias ApiResponse<S> = NetworkResponse<S, ResponseBody>
