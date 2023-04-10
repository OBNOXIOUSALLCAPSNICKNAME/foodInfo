package com.example.foodinfo.repository

import androidx.paging.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.foodinfo.local.dao.APICredentialsDAO
import com.example.foodinfo.local.dao.RecipeDAO
import com.example.foodinfo.local.dto.EdamamCredentialsDB
import com.example.foodinfo.local.dto.NutrientRecipeAttrDB
import com.example.foodinfo.local.dto.RecipeAttrsDB
import com.example.foodinfo.remote.api.RecipeAPI
import com.example.foodinfo.repository.mapper.*
import com.example.foodinfo.repository.model.*
import com.example.foodinfo.repository.state_handling.BaseRepository
import com.example.foodinfo.repository.state_handling.DataProvider
import com.example.foodinfo.repository.state_handling.State
import com.example.foodinfo.utils.*
import com.example.foodinfo.utils.edamam.EdamamRecipeURL
import com.example.foodinfo.utils.edamam.FieldSet
import com.example.foodinfo.utils.paging.*
import kotlinx.coroutines.flow.Flow
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
                MapPagingSource(
                    originalSource = recipeDAO.getFavorite(),
                    mapperDelegate = { it.toModelFavorite() }
                )
            }
        ).flow
    }

    suspend fun getFavoriteIds(): Set<String> {
        return recipeDAO.getFavoriteIds().toSet()
    }

    fun getFavoriteCount(): Flow<Int> {
        return recipeDAO.getFavoriteCount()
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getByFilter(
        pagingHelper: PageFetchHelper,
        pagingConfig: PagingConfig
    ): Flow<PagingData<RecipeShortModel>> {
        return Pager(
            config = pagingConfig,
            remoteMediator = EdamamRemoteMediator(
                query = pagingHelper.remoteQuery.value,
                remoteDataProvider = { pageURL -> recipeAPI.getPage(pageURL) },
                saveRemoteDelegate = { recipes -> recipeDAO.addRecipes(recipes) },
                mapToLocalDelegate = { hits -> hits.map { it.recipe.toDBSave(pagingHelper.attrs) } }
            ),
            pagingSourceFactory = {
                MapPagingSource(
                    originalSource = recipeDAO.getByFilter(SimpleSQLiteQuery(pagingHelper.localQuery.value)),
                    mapperDelegate = { it.toModelShort() }
                )
            }
        ).flow
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
            saveRemoteDelegate = { recipeDAO.addRecipe(it) },
            mapToLocalDelegate = { it.recipe.toDBSave(attrs) },
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

    fun getByIdIngredients(recipeID: String): Flow<State<List<IngredientOfRecipeModel>>> {
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

    suspend fun delFromFavorite(ID: List<String>) {
        recipeDAO.delFromFavorite(ID)
    }
}