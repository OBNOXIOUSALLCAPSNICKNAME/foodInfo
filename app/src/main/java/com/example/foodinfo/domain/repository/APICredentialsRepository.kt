package com.example.foodinfo.domain.repository

import com.example.foodinfo.domain.model.EdamamCredentials
import com.example.foodinfo.domain.model.GitHubCredentials


interface APICredentialsRepository {
    suspend fun getEdamam(name: String): EdamamCredentials

    suspend fun getGitHub(name: String): GitHubCredentials


    suspend fun addEdamam(credentials: EdamamCredentials)

    suspend fun addGitHub(credentials: GitHubCredentials)
}