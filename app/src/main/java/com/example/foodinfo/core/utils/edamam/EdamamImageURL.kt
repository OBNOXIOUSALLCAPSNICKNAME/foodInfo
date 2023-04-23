package com.example.foodinfo.core.utils.edamam


/**
 * Wrapper that helps to properly compare Edamam API image URL ignoring dynamically changing access token.
 */
data class EdamamImageURL(
    val baseURL: String,
    val token: String,
    val isExpired: Boolean
) {
    override fun equals(other: Any?): Boolean =
        other is EdamamImageURL &&
        this.baseURL == other.baseURL &&
        this.isExpired == other.isExpired

    override fun hashCode(): Int {
        var result = baseURL.hashCode()
        result = 31 * result + isExpired.hashCode()
        return result
    }

    override fun toString() = "$baseURL?$token"


    companion object {
        operator fun invoke(value: String): EdamamImageURL {
            value.split("?").also { url ->
                return EdamamImageURL(
                    baseURL = url[0],
                    token = url[1],
                    //TODO extract date from token and check if its already expired
                    /*
                        overridden equals() does not compare URL token to prevent redundant UI updates
                        each time recipe is fetched from network (as token changes each time).
                        It may lead to situations when token has expired but equals() returns true.
                        To avoid that, isExpired parameter is needed
                     */
                    isExpired = false
                )
            }
        }
    }
}