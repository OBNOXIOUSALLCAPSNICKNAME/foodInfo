package com.example.foodinfo.remote.response

import com.example.foodinfo.R
import com.example.foodinfo.utils.ErrorCodes
import com.example.foodinfo.utils.NoInternetException
import com.example.foodinfo.utils.UnknownException
import java.io.IOException


sealed class NetworkResponse<out S : Any, out E : Any> {

    data class Success<T : Any>(
        val result: T
    ) : NetworkResponse<T, Nothing>()

    interface Error {
        val code: Int
        val throwable: Throwable
        val messageID: Int
    }

    data class ClientError<T : Any, E : Any>(
        override val code: Int,
        val body: T? = null,
        val errorBody: E? = null
    ) : NetworkResponse<T, E>(), Error {
        override val throwable = IOException("$errorBody")
        override val messageID: Int = when (code) {
            ErrorCodes.CLIENT_BAD_REQUEST       -> R.string.error_client_400
            ErrorCodes.CLIENT_UNAUTHORIZED      -> R.string.error_client_401
            ErrorCodes.CLIENT_FORBIDDEN         -> R.string.error_client_403
            ErrorCodes.CLIENT_NOT_FOUND         -> R.string.error_client_404
            ErrorCodes.CLIENT_TOO_MANY_REQUESTS -> R.string.error_client_429
            else                                -> R.string.error_client
        }
    }

    data class ServerError<E : Any>(
        override val code: Int,
        val errorBody: E? = null
    ) : NetworkResponse<Nothing, E>(), Error {
        override val throwable = IOException("$errorBody")
        override val messageID: Int = R.string.error_server
    }

    data class NetworkError(
        override val code: Int = ErrorCodes.RESPONSE_NETWORK,
        override val throwable: Throwable = NoInternetException(),
        override val messageID: Int = R.string.error_no_internet
    ) : NetworkResponse<Nothing, Nothing>(), Error

    data class UnknownError(
        override val code: Int = ErrorCodes.RESPONSE_UNKNOWN,
        override val throwable: Throwable = UnknownException(),
        override val messageID: Int = R.string.error_unknown
    ) : NetworkResponse<Nothing, Nothing>(), Error
}