package com.example.foodinfo.data.remote.data_source

import com.example.foodinfo.domain.model.GitHubCredentials
import com.example.foodinfo.data.remote.model.RecipeAttrsNetwork
import com.example.foodinfo.utils.ApiResponse


interface RecipeAttrRemoteSource {
    suspend fun getRecipeAttrs(apiCredentials: GitHubCredentials): ApiResponse<RecipeAttrsNetwork>
}