package com.example.foodinfo.repository.model


/**
 * Wrapper that helps to properly compare Edamam API image URL ignoring dynamically changing access token.
 */
data class EdamamImageURL(
    val baseURL: String,
    val token: String
) {
    override fun equals(other: Any?) =
        other is EdamamImageURL &&
        this.baseURL == other.baseURL

    override fun hashCode(): Int {
        return baseURL.hashCode()
    }

    override fun toString() = "$baseURL?$token"

    companion object {
        fun fromString(value: String): EdamamImageURL {
            value.split("?").also { url ->
                return EdamamImageURL(url[0], url[1])
            }
        }
    }
}