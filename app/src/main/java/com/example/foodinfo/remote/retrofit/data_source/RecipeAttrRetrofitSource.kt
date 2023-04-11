package com.example.foodinfo.remote.retrofit.data_source

import com.example.foodinfo.remote.data_source.RecipeAttrRemoteSource
import com.example.foodinfo.remote.model.RecipeAttrsNetwork
import com.example.foodinfo.remote.retrofit.api.RecipeAttrAPI
import com.example.foodinfo.utils.ApiResponse
import javax.inject.Inject


class RecipeAttrRetrofitSource @Inject constructor(
    private val recipeAttrAPI: RecipeAttrAPI
) : RecipeAttrRemoteSource {
    override suspend fun getRecipeAttrs(token: String): ApiResponse<RecipeAttrsNetwork> {
        return recipeAttrAPI.getRecipeAttrs(token)
    }
}