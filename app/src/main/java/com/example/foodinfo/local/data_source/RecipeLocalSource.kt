package com.example.foodinfo.local.data_source

import androidx.paging.PagingSource
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.foodinfo.local.model.*
import kotlinx.coroutines.flow.Flow


interface RecipeLocalSource {

    fun getFavorite(): PagingSource<Int, out RecipeDB>

    fun getByFilter(query: SupportSQLiteQuery): PagingSource<Int, out RecipeDB>


    fun getByIdExtended(recipeID: String): Flow<RecipeExtendedDB>


    fun getIngredients(recipeID: String): Flow<List<IngredientOfRecipeDB>>

    fun getNutrients(recipeID: String): Flow<List<NutrientOfRecipeExtendedDB>>

    fun getLabels(recipeID: String): Flow<List<LabelOfRecipeExtendedDB>>


    fun getFavoriteCount(): Flow<Int>

    suspend fun getFavoriteIds(): List<String>

    suspend fun invertFavoriteStatus(recipeID: String)

    suspend fun delFromFavorite(recipeIDs: List<String>)


    // addRecipes() must not lose favoriteMark status when updating
    suspend fun addRecipes(recipes: List<RecipeToSaveDB>)

    // addRecipes() must not lose favoriteMark and not override lastUpdate when updating
    suspend fun addRecipe(recipe: RecipeToSaveDB)


    // addLabels(), addNutrients() and addIngredients()
    // must remove all content and insert new one
    suspend fun addLabels(labels: List<LabelOfRecipeDB>)

    suspend fun addNutrients(nutrients: List<NutrientOfRecipeDB>)

    suspend fun addIngredients(ingredients: List<IngredientOfRecipeDB>)
}