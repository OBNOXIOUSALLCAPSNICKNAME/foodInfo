package com.example.foodinfo.remote.retrofit.api

import com.example.foodinfo.remote.model.RecipeAttrsNetwork
import com.example.foodinfo.utils.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Header


interface RecipeAttrAPI {
    @GET("repos/yvo08013/SharedFiles/contents/db_fields_info.json")
    suspend fun getRecipeAttrs(
        @Header(AUTH_TOKEN)
        token: String
    ): ApiResponse<RecipeAttrsNetwork>


    private companion object {
        const val AUTH_TOKEN = "Authorization"
    }
}