package com.example.foodinfo.local.model


open class GitHubCredentialsDB(
    open val name: String = DEFAULT_NAME,
    open val token: String = "github_pat_11ALEMP6A08TAuQznQtdvC_Y5NZYh" + "4AfHeHjbFpSVuI3sRo9YZ2sUkYuTriz5LeBJA2D27UTCF99DGWvca",
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