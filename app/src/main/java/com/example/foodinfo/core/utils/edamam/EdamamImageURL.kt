package com.example.foodinfo.core.utils.edamam

import com.bumptech.glide.load.model.GlideUrl
import com.google.gson.internal.bind.util.ISO8601Utils
import java.net.URL
import java.text.ParsePosition


/**
 * Wrapper that helps to properly compare Edamam API image URL ignoring dynamically changing access token.
 */
data class EdamamImageURL(
    val baseURL: String,
    val token: String,
    val isExpired: Boolean
) : GlideUrl(baseURL) {

    override fun equals(other: Any?): Boolean =
        other is EdamamImageURL &&
        this.baseURL == other.baseURL &&
        this.isExpired == other.isExpired

    override fun hashCode(): Int {
        var result = baseURL.hashCode()
        result = 31 * result + isExpired.hashCode()
        return result
    }

    override fun toString() = baseURL


    override fun toStringUrl() = "$baseURL?$token"

    override fun toURL() = URL(this.toStringUrl())

    override fun getCacheKey() = toString()


    companion object {
        operator fun invoke(value: String): EdamamImageURL {
            value.split("?", limit = 2).also { (baseURL, token) ->
                return EdamamImageURL(
                    baseURL = baseURL,
                    token = token,
                    isExpired = isExpired(token)
                )
            }
        }

        private fun isExpired(token: String): Boolean {
            val tokenDate = ISO8601Utils.parse(
                token.substringAfter(EdamamInfo.IMAGE_TOKEN_FIELD),
                ParsePosition(0)
            )
            return (System.currentTimeMillis() - tokenDate.time) >= EdamamInfo.IMAGE_TOKEN_EXPIRATION_TIME
        }
    }
}