package com.example.foodinfo.remote.data_source

import com.example.foodinfo.domain.model.SearchFilterPresetModel
import com.example.foodinfo.local.model.EdamamCredentialsDB
import com.example.foodinfo.remote.model.RecipeHitNetwork
import com.example.foodinfo.remote.model.RecipePageNetwork
import com.example.foodinfo.utils.ApiResponse
import com.example.foodinfo.utils.edamam.FieldSet


interface RecipeRemoteSource {
    suspend fun getInitPage(
        apiCredentials: EdamamCredentialsDB,
        filterPreset: SearchFilterPresetModel,
        inputText: String,
        fieldSet: FieldSet
    ): ApiResponse<RecipePageNetwork>

    suspend fun getNextPage(href: String): ApiResponse<RecipePageNetwork>

    suspend fun getRecipe(
        apiCredentials: EdamamCredentialsDB,
        fieldSet: FieldSet,
        recipeID: String,
    ): ApiResponse<RecipeHitNetwork>
}