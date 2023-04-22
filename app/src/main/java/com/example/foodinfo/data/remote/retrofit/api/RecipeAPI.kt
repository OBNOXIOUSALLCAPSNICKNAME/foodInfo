package com.example.foodinfo.data.remote.retrofit.api

import com.example.foodinfo.core.utils.ApiResponse
import com.example.foodinfo.data.remote.model.IngredientOfRecipeNetwork
import com.example.foodinfo.data.remote.model.NutrientOfRecipeNetwork
import com.example.foodinfo.data.remote.model.RecipeHitNetwork
import com.example.foodinfo.data.remote.model.RecipePageNetwork
import retrofit2.http.GET
import retrofit2.http.Url


interface RecipeAPI {

    @GET
    suspend fun getPage(
        @Url
        url: String
    ): ApiResponse<RecipePageNetwork>

    @GET
    suspend fun getRecipe(
        @Url
        url: String
    ): ApiResponse<RecipeHitNetwork>

    @GET
    suspend fun getRecipeNutrients(
        @Url
        url: String
    ): ApiResponse<Map<String, NutrientOfRecipeNetwork>>

    @GET
    suspend fun getRecipeIngredients(
        @Url
        url: String
    ): ApiResponse<List<IngredientOfRecipeNetwork>>
}