package com.example.foodinfo.remote.retrofit.data_source

import com.example.foodinfo.domain.model.SearchFilterPresetModel
import com.example.foodinfo.local.model.EdamamCredentialsDB
import com.example.foodinfo.remote.data_source.RecipeRemoteSource
import com.example.foodinfo.remote.model.RecipeHitNetwork
import com.example.foodinfo.remote.model.RecipePageNetwork
import com.example.foodinfo.remote.retrofit.EdamamPageURL
import com.example.foodinfo.remote.retrofit.EdamamRecipeURL
import com.example.foodinfo.remote.retrofit.api.RecipeAPI
import com.example.foodinfo.utils.ApiResponse
import com.example.foodinfo.utils.edamam.FieldSet
import javax.inject.Inject


class RecipeRetrofitSource @Inject constructor(
    private val recipeAPI: RecipeAPI
) : RecipeRemoteSource {
    override suspend fun getInitPage(
        apiCredentials: EdamamCredentialsDB,
        filterPreset: SearchFilterPresetModel,
        inputText: String,
        fieldSet: FieldSet
    ): ApiResponse<RecipePageNetwork> {
        return recipeAPI.getPage(EdamamPageURL.build(apiCredentials, filterPreset, inputText, fieldSet))
    }

    override suspend fun getNextPage(href: String): ApiResponse<RecipePageNetwork> {
        return recipeAPI.getPage(href)
    }

    override suspend fun getRecipe(
        apiCredentials: EdamamCredentialsDB,
        fieldSet: FieldSet,
        recipeID: String,
    ): ApiResponse<RecipeHitNetwork> {
        return recipeAPI.getRecipe(EdamamRecipeURL.build(apiCredentials, fieldSet, recipeID))
    }
}