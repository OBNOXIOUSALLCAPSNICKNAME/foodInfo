package com.example.foodinfo.data.remote.retrofit.api

import com.example.foodinfo.core.utils.ApiResponse
import com.example.foodinfo.data.remote.model.RecipeMetadataNetwork
import retrofit2.http.GET
import retrofit2.http.Header


interface RecipeMetadataAPI {
    @GET("repos/yvo08013/SharedFiles/contents/db_fields_info.json")
    suspend fun getRecipeMetadata(
        @Header(AUTH_TOKEN)
        token: String
    ): ApiResponse<RecipeMetadataNetwork>


    private companion object {
        const val AUTH_TOKEN = "Authorization"
    }
}