package com.example.foodinfo.local.dao

import com.example.foodinfo.local.dto.EdamamCredentialsDB
import com.example.foodinfo.local.dto.GitHubCredentialsDB


interface APICredentialsDAO {
    fun getEdamam(name: String): EdamamCredentialsDB

    fun getGitHub(name: String): GitHubCredentialsDB

    // add functions must update content if it already in DB
    suspend fun addEdamam(credentials: EdamamCredentialsDB)

    suspend fun addGitHub(credentials: GitHubCredentialsDB)
}