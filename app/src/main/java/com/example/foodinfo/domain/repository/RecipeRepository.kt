package com.example.foodinfo.domain.repository

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
        pagingHelper: PagingHelper
    ): Flow<PagingData<Recipe>>

    fun getByIdExtended(
        apiCredentials: EdamamCredentials,
        metadata: RecipeMetadata,
        recipeID: String,
    ): Flow<State<RecipeExtended>>

    fun getByIdNutrients(
        apiCredentials: EdamamCredentials,
        metadata: List<NutrientOfRecipeMetadata>,
        recipeID: String
    ): Flow<State<List<NutrientOfRecipe>>>

    fun getByIdIngredients(
        apiCredentials: EdamamCredentials,
        recipeID: String
    ): Flow<State<List<IngredientOfRecipe>>>

    suspend fun invertFavoriteStatus(ID: String)

    suspend fun delFromFavorite(IDs: List<String>)
}