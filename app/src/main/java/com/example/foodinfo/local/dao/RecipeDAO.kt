package com.example.foodinfo.local.dao

import androidx.paging.PagingSource
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.foodinfo.local.dto.*
import kotlinx.coroutines.flow.Flow


interface RecipeDAO {

    fun getFavorite(): PagingSource<Int, out RecipeDB>

    fun getByFilter(query: SupportSQLiteQuery): PagingSource<Int, out RecipeDB>


    fun getByIdExtended(recipeID: String): Flow<@JvmWildcard RecipeExtendedDB>


    fun getIngredients(recipeID: String): Flow<@JvmWildcard List<@JvmWildcard IngredientOfRecipeDB>>

    fun getNutrients(recipeID: String): Flow<@JvmWildcard List<@JvmWildcard NutrientOfRecipeExtendedDB>>

    fun getLabels(recipeID: String): Flow<@JvmWildcard List<@JvmWildcard LabelOfRecipeExtendedDB>>


    fun getFavoriteIds(): List<String>

    fun invertFavoriteStatus(recipeID: String)

    fun delFromFavorite(recipeIDs: List<String>)


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
