package com.example.foodinfo.local.dao

import com.example.foodinfo.local.dto.EdamamCredentialsDB
import com.example.foodinfo.local.dto.GitHubCredentialsDB


interface APICredentialsDAO {
    fun getEdamam(name: String): EdamamCredentialsDB

    fun addEdamam(credentials: EdamamCredentialsDB)

    fun getGitHub(name: String): GitHubCredentialsDB

    fun addGitHub(credentials: GitHubCredentialsDB)
}