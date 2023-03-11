package com.example.foodinfo.remote.api

import com.example.foodinfo.remote.dto.RecipeAttrsNetwork
import com.example.foodinfo.remote.response.ApiResponse
import retrofit2.http.GET


interface RecipeAttrAPI {
    @GET("repos/yvo08013/SharedFiles/contents/db_fields_info.json")
    suspend fun getRecipeAttrs(): ApiResponse<RecipeAttrsNetwork>
}