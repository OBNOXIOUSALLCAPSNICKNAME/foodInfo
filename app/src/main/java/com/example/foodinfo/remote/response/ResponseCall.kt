package com.example.foodinfo.remote.response


import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

internal class ResponseCall<S : Any, E : Any>(
    private val delegate: Call<S>,
    private val errorConverter: Converter<ResponseBody, E>
) : Call<NetworkResponse<S, E>> {

    override fun enqueue(callback: Callback<NetworkResponse<S, E>>) {
        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()

                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@ResponseCall,
                            Response.success(NetworkResponse.Success(body))
                        )
                    } else {
                        callback.onResponse(
                            this@ResponseCall,
                            Response.success(NetworkResponse.UnknownError(code))
                        )
                    }
                } else {
                    val errorBody = when {
                        error == null               -> null
                        error.contentLength() == 0L -> null
                        else                        -> try {
                            errorConverter.convert(error)
                        } catch (ex: Exception) {
                            null
                        }
                    }
                    if (errorBody != null) {
                        callback.onResponse(
                            this@ResponseCall,
                            Response.success(
                                when (code) {
                                    in SERVER_ERROR_RANGE -> {
                                        NetworkResponse.ServerError(code, errorBody)
                                    }
                                    in CLIENT_ERROR_RANGE -> {
                                        NetworkResponse.ClientError(code, body, errorBody)
                                    }
                                    else                  -> {
                                        NetworkResponse.UnknownError(code)
                                    }
                                }
                            )
                        )
                    } else {
                        callback.onResponse(
                            this@ResponseCall,
                            Response.success(NetworkResponse.UnknownError(code))
                        )
                    }
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                val response = when (throwable) {
                    is IOException -> NetworkResponse.NetworkError(error = throwable)
                    else           -> NetworkResponse.UnknownError(error = throwable)
                }
                callback.onResponse(this@ResponseCall, Response.success(response))
            }
        })
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = ResponseCall(delegate.clone(), errorConverter)

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<NetworkResponse<S, E>> {
        throw UnsupportedOperationException("ApiResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}