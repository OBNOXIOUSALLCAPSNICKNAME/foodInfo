package com.example.foodinfo.data.remote.retrofit.data_source

import com.example.foodinfo.core.utils.ApiResponse
import com.example.foodinfo.data.remote.data_source.RecipeMetadataRemoteSource
import com.example.foodinfo.data.remote.model.RecipeMetadataNetwork
import com.example.foodinfo.data.remote.retrofit.api.RecipeMetadataAPI
import com.example.foodinfo.domain.model.GitHubCredentials
import javax.inject.Inject


class RecipeMetadataRetrofitSource @Inject constructor(
    private val recipeMetadataAPI: RecipeMetadataAPI
) : RecipeMetadataRemoteSource {
    override suspend fun getRecipeMetadata(
        apiCredentials: GitHubCredentials
    ): ApiResponse<RecipeMetadataNetwork> {
        return recipeMetadataAPI.getRecipeMetadata("${GitHubCredentials.TOKEN_PREFIX} ${apiCredentials.token}")
    }
}