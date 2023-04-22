package com.example.foodinfo.data.repository

import com.example.foodinfo.data.local.data_source.APICredentialsLocalSource
import com.example.foodinfo.data.mapper.toDB
import com.example.foodinfo.data.mapper.toModel
import com.example.foodinfo.domain.model.EdamamCredentials
import com.example.foodinfo.domain.model.GitHubCredentials
import com.example.foodinfo.domain.repository.APICredentialsRepository
import javax.inject.Inject


class APICredentialsRepositoryImpl @Inject constructor(
    private val apiCredentialsLocalSource: APICredentialsLocalSource
) : BaseRepository(), APICredentialsRepository {
    override suspend fun getEdamam(name: String): EdamamCredentials {
        return apiCredentialsLocalSource.getEdamam(name).toModel()
    }

    override suspend fun getGitHub(name: String): GitHubCredentials {
        return apiCredentialsLocalSource.getGitHub(name).toModel()
    }


    override suspend fun addEdamam(credentials: EdamamCredentials) {
        apiCredentialsLocalSource.addEdamam(credentials.toDB())
    }

    override suspend fun addGitHub(credentials: GitHubCredentials) {
        apiCredentialsLocalSource.addGitHub(credentials.toDB())
    }
}