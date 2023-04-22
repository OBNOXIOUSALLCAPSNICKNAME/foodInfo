package com.example.foodinfo.data.remote.data_source

import com.example.foodinfo.core.utils.ApiResponse
import com.example.foodinfo.data.remote.model.RecipeMetadataNetwork
import com.example.foodinfo.domain.model.GitHubCredentials


interface RecipeMetadataRemoteSource {
    suspend fun getRecipeMetadata(apiCredentials: GitHubCredentials): ApiResponse<RecipeMetadataNetwork>
}