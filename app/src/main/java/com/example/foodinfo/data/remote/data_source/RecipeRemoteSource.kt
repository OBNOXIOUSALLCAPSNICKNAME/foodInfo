package com.example.foodinfo.data.remote.data_source

import com.example.foodinfo.domain.model.EdamamCredentials
import com.example.foodinfo.domain.model.SearchFilterPreset
import com.example.foodinfo.data.remote.model.RecipeHitNetwork
import com.example.foodinfo.data.remote.model.RecipePageNetwork
import com.example.foodinfo.utils.ApiResponse
import com.example.foodinfo.utils.edamam.FieldSet


interface RecipeRemoteSource {
    suspend fun getInitPage(
        apiCredentials: EdamamCredentials,
        filterPreset: SearchFilterPreset,
        inputText: String,
        fieldSet: FieldSet
    ): ApiResponse<RecipePageNetwork>

    suspend fun getNextPage(href: String): ApiResponse<RecipePageNetwork>

    suspend fun getRecipe(
        apiCredentials: EdamamCredentials,
        fieldSet: FieldSet,
        recipeID: String,
    ): ApiResponse<RecipeHitNetwork>
}