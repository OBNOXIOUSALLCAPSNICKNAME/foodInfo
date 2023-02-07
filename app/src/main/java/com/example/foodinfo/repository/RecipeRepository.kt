package com.example.foodinfo.repository

import androidx.paging.PagingData
import com.example.foodinfo.local.dto.NutrientRecipeAttrDB
import com.example.foodinfo.local.dto.RecipeAttrsDB
import com.example.foodinfo.repository.model.*
import com.example.foodinfo.utils.State
import kotlinx.coroutines.flow.Flow


abstract class RecipeRepository : BaseRepository() {
    abstract fun getPopular(): Flow<PagingData<RecipeShortModel>>

    abstract fun getFavorite(): Flow<PagingData<RecipeFavoriteModel>>

    abstract fun getFavoriteIds(): List<String>

    abstract fun getByFilter(query: String): Flow<PagingData<RecipeShortModel>>

    abstract fun getByIdExtended(
        recipeID: String,
        attrs: RecipeAttrsDB
    ): Flow<State<RecipeExtendedModel>>

    abstract fun getByIdNutrients(
        recipeID: String,
        attrs: List<NutrientRecipeAttrDB>
    ): Flow<State<List<NutrientOfRecipeModel>>>

    abstract fun getByIdIngredients(recipeID: String): Flow<State<List<RecipeIngredientModel>>>


    abstract fun invertFavoriteStatus(ID: String)

    abstract fun delFromFavorite(ID: List<String>)
}