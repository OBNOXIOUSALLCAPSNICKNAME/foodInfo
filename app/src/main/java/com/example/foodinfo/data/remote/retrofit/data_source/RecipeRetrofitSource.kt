package com.example.foodinfo.data.remote.retrofit.data_source

import com.example.foodinfo.domain.model.EdamamCredentials
import com.example.foodinfo.domain.model.SearchFilterPreset
import com.example.foodinfo.data.remote.data_source.RecipeRemoteSource
import com.example.foodinfo.data.remote.model.RecipeHitNetwork
import com.example.foodinfo.data.remote.model.RecipePageNetwork
import com.example.foodinfo.data.remote.retrofit.EdamamPageURL
import com.example.foodinfo.data.remote.retrofit.EdamamRecipeURL
import com.example.foodinfo.data.remote.retrofit.api.RecipeAPI
import com.example.foodinfo.utils.ApiResponse
import com.example.foodinfo.utils.edamam.FieldSet
import javax.inject.Inject


class RecipeRetrofitSource @Inject constructor(
    private val recipeAPI: RecipeAPI
) : RecipeRemoteSource {
    override suspend fun getInitPage(
        apiCredentials: EdamamCredentials,
        filterPreset: SearchFilterPreset,
        inputText: String,
        fieldSet: FieldSet
    ): ApiResponse<RecipePageNetwork> {
        return recipeAPI.getPage(EdamamPageURL.build(apiCredentials, filterPreset, inputText, fieldSet))
    }

    override suspend fun getNextPage(href: String): ApiResponse<RecipePageNetwork> {
        return recipeAPI.getPage(href)
    }

    override suspend fun getRecipe(
        apiCredentials: EdamamCredentials,
        fieldSet: FieldSet,
        recipeID: String,
    ): ApiResponse<RecipeHitNetwork> {
        return recipeAPI.getRecipe(EdamamRecipeURL.build(apiCredentials, fieldSet, recipeID))
    }
}