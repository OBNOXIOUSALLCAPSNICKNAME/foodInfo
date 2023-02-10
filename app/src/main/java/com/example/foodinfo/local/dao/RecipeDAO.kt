package com.example.foodinfo.local.dao

import androidx.paging.PagingSource
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.foodinfo.local.dto.*
import kotlinx.coroutines.flow.Flow


interface RecipeDAO {

    // return recipes in random order (in case there is no such attribute as "Popular" for recipe yet)
    fun getPopular(): PagingSource<Int, out RecipeDB>

    fun getFavorite(): PagingSource<Int, out RecipeDB>

    fun getByFilter(query: SupportSQLiteQuery): PagingSource<Int, out RecipeDB>


    fun getByIdExtended(recipeID: String): Flow<@JvmSuppressWildcards(suppress = false) RecipeExtendedDB>


    fun getIngredients(recipeID: String): Flow<@JvmSuppressWildcards(suppress = false) List<IngredientOfRecipeDB>>

    fun getNutrients(recipeID: String): Flow<@JvmSuppressWildcards(suppress = false) List<NutrientOfRecipeExtendedDB>>

    fun getLabels(recipeID: String): Flow<@JvmSuppressWildcards(suppress = false) List<LabelOfRecipeExtendedDB>>


    fun getFavoriteIds(): List<String>

    fun invertFavoriteStatus(recipeID: String)

    fun delFromFavorite(recipeIDs: List<String>)


    // removeRecipe() and removeRecipes() must also delete recipe ingredients, nutrients and labels
    fun removeRecipe(recipeID: String)

    fun removeRecipes(recipeIDs: List<String>)


    // addRecipes() and addRecipeExtended() must not lose favoriteMark status when updating
    fun addRecipes(recipes: List<RecipeDB>)

    fun addRecipeExtended(recipe: RecipeExtendedDB)


    // addLabels(), addNutrients() and addIngredients()
    // must firstly remove all labels/nutrients/ingredients for recipes that already in DB
    fun addLabels(labels: List<LabelOfRecipeDB>)

    fun addNutrients(nutrients: List<NutrientOfRecipeDB>)

    fun addIngredients(ingredients: List<IngredientOfRecipeDB>)
}
