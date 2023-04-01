package com.example.foodinfo.utils.edamam


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
        operator fun invoke(value: String): EdamamImageURL {
            value.split("?").also { url ->
                return EdamamImageURL(url[0], url[1])
            }
        }
    }
}