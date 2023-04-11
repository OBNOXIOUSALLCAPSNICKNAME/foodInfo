package com.example.foodinfo.remote.data_source

import com.example.foodinfo.remote.model.RecipeAttrsNetwork
import com.example.foodinfo.utils.ApiResponse


interface RecipeAttrRemoteSource {
    suspend fun getRecipeAttrs(token: String): ApiResponse<RecipeAttrsNetwork>
}