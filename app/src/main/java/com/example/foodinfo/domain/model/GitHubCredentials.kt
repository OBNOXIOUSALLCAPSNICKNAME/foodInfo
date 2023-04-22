package com.example.foodinfo.domain.model


data class GitHubCredentials(
    val name: String,
    val token: String,
) {
    companion object {
        const val TOKEN_PREFIX = "Bearer"
    }
}