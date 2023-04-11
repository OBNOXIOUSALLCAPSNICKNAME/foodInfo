package com.example.foodinfo.remote.data_source

import com.example.foodinfo.remote.model.RecipeHitNetwork
import com.example.foodinfo.remote.model.RecipePageNetwork
import com.example.foodinfo.utils.ApiResponse


interface RecipeRemoteSource {
    suspend fun getPage(url: String): ApiResponse<RecipePageNetwork>

    suspend fun getRecipe(url: String): ApiResponse<RecipeHitNetwork>
}