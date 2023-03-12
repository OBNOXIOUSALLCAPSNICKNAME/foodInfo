package com.example.foodinfo.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.foodinfo.local.dao.APICredentialsDAO
import com.example.foodinfo.local.dao.RecipeDAO
import com.example.foodinfo.local.dto.EdamamCredentialsDB
import com.example.foodinfo.local.dto.NutrientRecipeAttrDB
import com.example.foodinfo.local.dto.RecipeAttrsDB
import com.example.foodinfo.remote.api.RecipeAPI
import com.example.foodinfo.repository.mapper.*
import com.example.foodinfo.repository.model.*
import com.example.foodinfo.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class RecipeRepository @Inject constructor(
    private val apiCredentialsDAO: APICredentialsDAO,
    private val recipeDAO: RecipeDAO,
    private val recipeAPI: RecipeAPI,
    private val prefUtils: PrefUtils
) : BaseRepository() {

    fun getFavorite(): Flow<PagingData<RecipeFavoriteModel>> {
        return Pager(
            config = DB_FAVORITE_PAGER,
            pagingSourceFactory = {
                recipeDAO.getFavorite()
            }
        ).flow.map { pagingData -> pagingData.map { it.toModelFavorite() } }.flowOn(Dispatchers.IO)
    }

    fun getFavoriteIds(): List<String> {
        return recipeDAO.getFavoriteIds()
    }

    fun getByFilter(
        searchFilterPreset: SearchFilterPresetModel,
        inputText: String
    ): Flow<PagingData<RecipeShortModel>> {
        val query = RecipePageQuery(
            searchFilterPreset = searchFilterPreset,
            apiCredentials = apiCredentialsDAO.getEdamam(prefUtils.edamamCredentials),
            inputText = inputText,
            isOffline = true
        )
        return Pager(
            config = DB_EXPLORE_PAGER,
            pagingSourceFactory = {
                recipeDAO.getByFilter(SimpleSQLiteQuery(query.local.value))
            }
        ).flow.map { pagingData -> pagingData.map { it.toModelShort() } }.flowOn(Dispatchers.IO)
    }

    fun getByIdExtended(
        recipeID: String,
        attrs: RecipeAttrsDB
    ): Flow<State<RecipeExtendedModel>> {
        return getData(
            remoteDataProvider = {
                DataProvider.Remote(
                    recipeAPI.getRecipe(EdamamRecipeURL(recipeID, FieldSet.FULL, apiCredentials).value)
                )
            },
            localDataProvider = { DataProvider.LocalFlow(recipeDAO.getByIdExtended(recipeID)) },
            saveRemoteDelegate = { recipeDAO.addRecipeExtended(it) },
            mapToLocalDelegate = { it.recipe.toDBExtended(attrs) },
            mapToModelDelegate = { it.toModelExtended() }
        )
    }

    fun getByIdNutrients(
        recipeID: String,
        attrs: List<NutrientRecipeAttrDB>
    ): Flow<State<List<NutrientOfRecipeModel>>> {
        return getData(
            remoteDataProvider = {
                DataProvider.Remote(
                    recipeAPI.getRecipe(EdamamRecipeURL(recipeID, FieldSet.NUTRIENTS, apiCredentials).value)
                )
            },
            localDataProvider = { DataProvider.LocalFlow(recipeDAO.getNutrients(recipeID)) },
            saveRemoteDelegate = { recipeDAO.addNutrients(it) },
            mapToLocalDelegate = { it.recipe.nutrients!!.toDB(recipeID, attrs) },
            mapToModelDelegate = { it.map { nutrient -> nutrient.toModel() } }
        )
    }

    fun getByIdIngredients(recipeID: String): Flow<State<List<RecipeIngredientModel>>> {
        return getData(
            remoteDataProvider = {
                DataProvider.Remote(
                    recipeAPI.getRecipe(EdamamRecipeURL(recipeID, FieldSet.INGREDIENTS, apiCredentials).value)
                )
            },
            localDataProvider = { DataProvider.LocalFlow(recipeDAO.getIngredients(recipeID)) },
            saveRemoteDelegate = { recipeDAO.addIngredients(it) },
            mapToLocalDelegate = { it.recipe.ingredients!!.map { ingredient -> ingredient.toDB(recipeID) } },
            mapToModelDelegate = { it.map { ingredient -> ingredient.toModel() } }
        )
    }

    fun invertFavoriteStatus(ID: String) {
        recipeDAO.invertFavoriteStatus(ID)
    }

    fun delFromFavorite(ID: List<String>) {
        recipeDAO.delFromFavorite(ID)
    }


    // definitely this is the wrong place to store pager configs but dunno where else
    companion object {
        val DB_EXPLORE_PAGER = PagingConfig(
            pageSize = 10,
            initialLoadSize = 20,
            jumpThreshold = 40,
            maxSize = 40
        )
        val DB_FAVORITE_PAGER = PagingConfig(
            pageSize = 10,
            initialLoadSize = 20,
            jumpThreshold = 40,
            maxSize = 40
        )
    }

    private val apiCredentials: EdamamCredentialsDB
        get() = apiCredentialsDAO.getEdamam(prefUtils.edamamCredentials)
}