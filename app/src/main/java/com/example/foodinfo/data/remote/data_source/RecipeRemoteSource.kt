package com.example.foodinfo.data.remote.data_source

import com.example.foodinfo.core.utils.ApiResponse
import com.example.foodinfo.data.remote.model.IngredientOfRecipeNetwork
import com.example.foodinfo.data.remote.model.NutrientOfRecipeNetwork
import com.example.foodinfo.data.remote.model.RecipeHitNetwork
import com.example.foodinfo.data.remote.model.RecipePageNetwork
import com.example.foodinfo.domain.model.EdamamCredentials
import com.example.foodinfo.domain.model.SearchFilterPreset


interface RecipeRemoteSource {
    suspend fun getInitPage(
        apiCredentials: EdamamCredentials,
        filterPreset: SearchFilterPreset,
        inputText: String,
    ): ApiResponse<RecipePageNetwork>

    suspend fun getNextPage(href: String): ApiResponse<RecipePageNetwork>

    suspend fun getRecipe(
        apiCredentials: EdamamCredentials,
        recipeID: String,
    ): ApiResponse<RecipeHitNetwork>

    suspend fun getRecipeNutrients(
        apiCredentials: EdamamCredentials,
        recipeID: String,
    ): ApiResponse<Map<String, NutrientOfRecipeNetwork>>

    suspend fun getRecipeIngredients(
        apiCredentials: EdamamCredentials,
        recipeID: String,
    ): ApiResponse<List<IngredientOfRecipeNetwork>>
}