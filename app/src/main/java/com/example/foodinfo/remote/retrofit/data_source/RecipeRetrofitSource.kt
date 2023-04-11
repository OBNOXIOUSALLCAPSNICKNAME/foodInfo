package com.example.foodinfo.remote.retrofit.data_source

import com.example.foodinfo.remote.data_source.RecipeRemoteSource
import com.example.foodinfo.remote.model.RecipeHitNetwork
import com.example.foodinfo.remote.model.RecipePageNetwork
import com.example.foodinfo.remote.retrofit.api.RecipeAPI
import com.example.foodinfo.utils.ApiResponse
import javax.inject.Inject


class RecipeRetrofitSource @Inject constructor(
    private val recipeAPI: RecipeAPI
) : RecipeRemoteSource {
    override suspend fun getPage(url: String): ApiResponse<RecipePageNetwork> {
        return recipeAPI.getPage(url)
    }

    override suspend fun getRecipe(url: String): ApiResponse<RecipeHitNetwork> {
        return recipeAPI.getRecipe(url)
    }
}