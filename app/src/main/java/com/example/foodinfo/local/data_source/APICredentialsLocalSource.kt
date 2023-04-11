package com.example.foodinfo.local.data_source

import com.example.foodinfo.local.model.EdamamCredentialsDB
import com.example.foodinfo.local.model.GitHubCredentialsDB


interface APICredentialsLocalSource {
    suspend fun getEdamam(name: String): EdamamCredentialsDB

    suspend fun getGitHub(name: String): GitHubCredentialsDB

    // add functions must update content if it already in DB
    suspend fun addEdamam(credentials: EdamamCredentialsDB)

    suspend fun addGitHub(credentials: GitHubCredentialsDB)
}