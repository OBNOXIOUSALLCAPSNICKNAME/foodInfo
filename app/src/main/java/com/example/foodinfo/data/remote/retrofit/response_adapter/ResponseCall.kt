package com.example.foodinfo.data.remote.retrofit.response_adapter


import com.example.foodinfo.core.utils.ErrorCodes
import com.example.foodinfo.data.remote.NetworkResponse
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
                val errorBody = response.errorBody()

                val networkResponse = if (response.isSuccessful) {
                    if (body != null) {
                        NetworkResponse.Success(body)
                    } else {
                        NetworkResponse.UnknownError(code)
                    }
                } else {
                    val convertedErrorBody = when {
                        errorBody == null               -> null
                        errorBody.contentLength() == 0L -> null
                        else                            -> try {
                            errorConverter.convert(errorBody)
                        } catch (e: Exception) {
                            null
                        }
                    }
                    when (code) {
                        in ErrorCodes.CLIENT_RANGE -> {
                            NetworkResponse.ClientError(code, body, convertedErrorBody)
                        }
                        in ErrorCodes.SERVER_RANGE -> {
                            NetworkResponse.ServerError(code, convertedErrorBody)
                        }
                        else                       -> {
                            NetworkResponse.UnknownError(code)
                        }
                    }
                }
                callback.onResponse(this@ResponseCall, Response.success(networkResponse))
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                val networkResponse = when (throwable) {
                    is IOException -> NetworkResponse.NetworkError(throwable = throwable)
                    else           -> NetworkResponse.UnknownError(throwable = throwable)
                }
                callback.onResponse(this@ResponseCall, Response.success(networkResponse))
            }
        })
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = ResponseCall(delegate.clone(), errorConverter)

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<NetworkResponse<S, E>> {
        throw UnsupportedOperationException("ResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}