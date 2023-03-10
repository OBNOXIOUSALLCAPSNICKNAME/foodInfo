package com.example.foodinfo.local.dao

import com.example.foodinfo.local.dto.APICredentialsDB


interface APICredentialsDAO {
    fun getCredentials(name: String): APICredentialsDB

    fun addCredentials(credentials: APICredentialsDB)
}