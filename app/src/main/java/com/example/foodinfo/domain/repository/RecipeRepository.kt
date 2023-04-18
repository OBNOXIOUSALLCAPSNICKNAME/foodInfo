package com.example.foodinfo.domain.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.model.*
import kotlinx.coroutines.flow.Flow


interface RecipeRepository {

    fun getFavorite(): Flow<PagingData<Recipe>>

    suspend fun getFavoriteIds(): Set<String>

    fun getFavoriteCount(): Flow<Int>

    fun getByFilter(
        apiCredentials: EdamamCredentials,
        pagingConfig: PagingConfig,
        filterPreset: SearchFilterPreset,
        recipeAttrs: RecipeAttrs,
        inputText: String = "",
        isOnline: Boolean
    ): Flow<PagingData<Recipe>>

    fun getByIdExtended(
        apiCredentials: EdamamCredentials,
        recipeID: String,
        attrs: RecipeAttrs
    ): Flow<State<RecipeExtended>>

    fun getByIdNutrients(
        apiCredentials: EdamamCredentials,
        recipeID: String,
        attrs: List<NutrientRecipeAttr>
    ): Flow<State<List<NutrientOfRecipe>>>

    fun getByIdIngredients(
        apiCredentials: EdamamCredentials,
        recipeID: String
    ): Flow<State<List<IngredientOfRecipe>>>

    suspend fun invertFavoriteStatus(ID: String)

    suspend fun delFromFavorite(ID: List<String>)
}