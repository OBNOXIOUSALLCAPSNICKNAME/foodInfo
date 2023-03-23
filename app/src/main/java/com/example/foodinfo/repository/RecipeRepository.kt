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
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class RecipeRepository @Inject constructor(
    private val apiCredentialsDAO: APICredentialsDAO,
    private val recipeDAO: RecipeDAO,
    private val recipeAPI: RecipeAPI,
    private val prefUtils: PrefUtils
) : BaseRepository() {

    private val apiCredentials: EdamamCredentialsDB
        get() = apiCredentialsDAO.getEdamam(prefUtils.edamamCredentials)


    fun getFavorite(): Flow<PagingData<RecipeFavoriteModel>> {
        return Pager(
            config = AppPagingConfig.RECIPE_FAVORITE_PAGER,
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
        pagingConfig: PagingConfig,
        inputText: String = ""
    ) = flow {

        // emit empty PagingData to be able to immediately handle loading state
        // because RecipePageQuery building may take some time
        emit(PagingData.empty())

        val query = RecipePageQuery(
            searchFilterPreset = searchFilterPreset,
            apiCredentials = apiCredentials,
            inputText = inputText,
            isOffline = true
        )
        emitAll(
            Pager(
                config = pagingConfig,
                pagingSourceFactory = {
                    recipeDAO.getByFilter(SimpleSQLiteQuery(query.local.value))
                }
            ).flow.map { pagingData -> pagingData.map { it.toModelShort() } }
        )
    }.flowOn(Dispatchers.IO)


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
}