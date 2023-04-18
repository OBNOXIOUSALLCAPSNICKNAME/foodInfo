package com.example.foodinfo.data.remote.retrofit.data_source

import com.example.foodinfo.domain.model.GitHubCredentials
import com.example.foodinfo.data.remote.data_source.RecipeAttrRemoteSource
import com.example.foodinfo.data.remote.model.RecipeAttrsNetwork
import com.example.foodinfo.data.remote.retrofit.api.RecipeAttrAPI
import com.example.foodinfo.utils.ApiResponse
import javax.inject.Inject


class RecipeAttrRetrofitSource @Inject constructor(
    private val recipeAttrAPI: RecipeAttrAPI
) : RecipeAttrRemoteSource {
    override suspend fun getRecipeAttrs(apiCredentials: GitHubCredentials): ApiResponse<RecipeAttrsNetwork> {
        return recipeAttrAPI.getRecipeAttrs("${GitHubCredentials.TOKEN_PREFIX} ${apiCredentials.token}")
    }
}