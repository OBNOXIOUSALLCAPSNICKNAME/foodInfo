package com.example.foodinfo.repository

import androidx.paging.PagingData
import com.example.foodinfo.repository.model.*
import com.example.foodinfo.utils.State
import kotlinx.coroutines.flow.Flow


interface RecipeRepository {
    fun getPopular(): Flow<PagingData<RecipeShortModel>>

    fun getFavorite(): Flow<PagingData<RecipeFavoriteModel>>

    fun getFavoriteIds(): List<String>

    fun getByFilter(query: String): Flow<PagingData<RecipeShortModel>>

    fun getByIdExtended(recipeID: String): Flow<State<RecipeExtendedModel>>

    fun getByIdIngredients(recipeID: String): Flow<State<List<RecipeIngredientModel>>>

    fun getByIdNutrients(recipeID: String): Flow<State<List<NutrientOfRecipeModel>>>

    fun getByIdLabels(recipeID: String): Flow<State<List<CategoryOfRecipeModel>>>

    fun invertFavoriteStatus(ID: String)

    fun delFromFavorite(ID: List<String>)
}