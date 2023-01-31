package com.example.foodinfo.local.dao

import androidx.paging.PagingSource
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.foodinfo.local.dto.*
import kotlinx.coroutines.flow.Flow


// All UPDATE functions must truly UPDATE content (not DELETE and INSERT) so all indices will stay the same
interface RecipeDAO {

    // return recipes in random order (in case there is no such attribute as "Popular" for recipe yet)
    fun getPopular(): PagingSource<Int, out RecipeDB>

    fun getFavorite(): PagingSource<Int, out RecipeDB>

    fun getByFilter(query: SupportSQLiteQuery): PagingSource<Int, out RecipeDB>


    fun getByIdExtended(recipeID: String): Flow<RecipeExtendedDB>


    fun getIngredients(recipeID: String): Flow<List<IngredientOfRecipeDB>>

    fun getNutrients(recipeID: String): Flow<List<NutrientOfRecipeExtendedDB>>

    fun getLabels(recipeID: String): Flow<List<LabelOfRecipeExtendedDB>>


    fun getFavoriteIds(): List<String>

    fun invertFavoriteStatus(recipeID: String)

    fun delFromFavorite(recipeIDs: List<String>)


    // must also delete recipe ingredients, nutrients and labels
    fun removeRecipes(recipeIds: List<String>)

    fun addRecipes(
        recipes: List<RecipeDB>,
        nutrients: List<NutrientOfRecipeDB>,
        ingredients: List<IngredientOfRecipeDB>,
        labels: List<LabelOfRecipeDB>
    )
}
