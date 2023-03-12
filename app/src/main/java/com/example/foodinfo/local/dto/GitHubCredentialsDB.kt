package com.example.foodinfo.local.dto


open class GitHubCredentialsDB(
    open val name: String = DEFAULT_NAME,
    open val token: String = "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",
) {

    object Columns {
        const val NAME = "name"
        const val TOKEN = "token"
    }

    companion object {
        const val TOKEN_PREFIX = "Bearer "
        const val DEFAULT_NAME = "default credentials"
        const val TABLE_NAME = "github_credentials"
    }
}