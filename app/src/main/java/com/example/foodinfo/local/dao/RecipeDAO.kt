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


    // addRecipes() and addRecipeExtended() must not lose favoriteMark status when updating
    // addRecipes() must save labels/ingredients/nutrients when updating recipes that already in DB
    fun addRecipes(recipes: List<RecipeDB>)

    /*
        When adding recipe from server into local DB, there is no way to check whether some
        nutrients/ingredients/labels was removed or added.
        So, if recipe already exists in local DB, all related data had to be removed and new one inserted
     */
    fun addRecipeExtended(recipe: RecipeExtendedDB)


    // addLabels(), addNutrients() and addIngredients()
    // must remove all content and insert new one
    fun addLabels(labels: List<LabelOfRecipeDB>)

    fun addNutrients(nutrients: List<NutrientOfRecipeDB>)

    fun addIngredients(ingredients: List<IngredientOfRecipeDB>)
}
