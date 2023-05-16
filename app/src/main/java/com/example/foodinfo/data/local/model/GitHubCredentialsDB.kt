package com.example.foodinfo.data.local.model


open class GitHubCredentialsDB(
    open val name: String = DEFAULT_NAME,
    open val token: String = "ghp_9108zdedgJ62R" + "mVm2RDDJ0GfcYAyD23EqabV",
) {
    object Columns {
        const val NAME = "name"
        const val TOKEN = "token"
    }

    companion object {
        const val DEFAULT_NAME = "default"
        const val TABLE_NAME = "github_credentials"
    }
}