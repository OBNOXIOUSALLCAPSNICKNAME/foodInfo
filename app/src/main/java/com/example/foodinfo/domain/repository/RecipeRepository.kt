package com.example.foodinfo.domain.repository

import androidx.paging.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.foodinfo.local.data_source.APICredentialsLocalSource
import com.example.foodinfo.local.data_source.RecipeLocalSource
import com.example.foodinfo.local.model.EdamamCredentialsDB
import com.example.foodinfo.local.model.NutrientRecipeAttrDB
import com.example.foodinfo.local.model.RecipeAttrsDB
import com.example.foodinfo.remote.data_source.RecipeRemoteSource
import com.example.foodinfo.domain.mapper.*
import com.example.foodinfo.domain.model.*
import com.example.foodinfo.domain.state.BaseRepository
import com.example.foodinfo.domain.state.DataProvider
import com.example.foodinfo.domain.state.State
import com.example.foodinfo.utils.*
import com.example.foodinfo.utils.edamam.EdamamRecipeURL
import com.example.foodinfo.utils.edamam.FieldSet
import com.example.foodinfo.utils.paging.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RecipeRepository @Inject constructor(
    private val apiCredentialsLocal: APICredentialsLocalSource,
    private val recipeLocal: RecipeLocalSource,
    private val recipeRemote: RecipeRemoteSource,
    private val prefUtils: PrefUtils
) : BaseRepository() {

    fun getFavorite(): Flow<PagingData<RecipeFavoriteModel>> {
        return Pager(
            config = AppPagingConfig.RECIPE_FAVORITE_PAGER,
            pagingSourceFactory = {
                MapPagingSource(
                    originalSource = recipeLocal.getFavorite(),
                    mapperDelegate = { it.toModelFavorite() }
                )
            }
        ).flow
    }

    suspend fun getFavoriteIds(): Set<String> {
        return recipeLocal.getFavoriteIds().toSet()
    }

    fun getFavoriteCount(): Flow<Int> {
        return recipeLocal.getFavoriteCount()
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
                remoteDataProvider = { pageURL -> recipeRemote.getPage(pageURL) },
                saveRemoteDelegate = { recipes -> recipeLocal.addRecipes(recipes) },
                mapToLocalDelegate = { hits -> hits.map { it.recipe.toDBSave(pagingHelper.attrs) } }
            ),
            pagingSourceFactory = {
                MapPagingSource(
                    originalSource = recipeLocal.getByFilter(SimpleSQLiteQuery(pagingHelper.localQuery.value)),
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
                    recipeRemote.getRecipe(
                        EdamamRecipeURL(
                            recipeID = recipeID,
                            fieldSet = FieldSet.FULL,
                            apiCredentials = getCredentials()
                        ).value
                    )
                )
            },
            localDataProvider = { DataProvider.LocalFlow(recipeLocal.getByIdExtended(recipeID)) },
            saveRemoteDelegate = { recipeLocal.addRecipe(it) },
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
                    recipeRemote.getRecipe(
                        EdamamRecipeURL(
                            recipeID = recipeID,
                            fieldSet = FieldSet.NUTRIENTS,
                            apiCredentials = getCredentials()
                        ).value
                    )
                )
            },
            localDataProvider = { DataProvider.LocalFlow(recipeLocal.getNutrients(recipeID)) },
            saveRemoteDelegate = { recipeLocal.addNutrients(it) },
            mapToLocalDelegate = { it.recipe.nutrients!!.toDB(recipeID, attrs) },
            mapToModelDelegate = { it.map { nutrient -> nutrient.toModel() } }
        )
    }

    fun getByIdIngredients(recipeID: String): Flow<State<List<IngredientOfRecipeModel>>> {
        return getData(
            remoteDataProvider = {
                DataProvider.Remote(
                    recipeRemote.getRecipe(
                        EdamamRecipeURL(
                            recipeID = recipeID,
                            fieldSet = FieldSet.INGREDIENTS,
                            apiCredentials = getCredentials()
                        ).value
                    )
                )
            },
            localDataProvider = { DataProvider.LocalFlow(recipeLocal.getIngredients(recipeID)) },
            saveRemoteDelegate = { recipeLocal.addIngredients(it) },
            mapToLocalDelegate = { it.recipe.ingredients!!.map { ingredient -> ingredient.toDB(recipeID) } },
            mapToModelDelegate = { it.map { ingredient -> ingredient.toModel() } }
        )
    }

    suspend fun invertFavoriteStatus(ID: String) {
        recipeLocal.invertFavoriteStatus(ID)
    }

    suspend fun delFromFavorite(ID: List<String>) {
        recipeLocal.delFromFavorite(ID)
    }


    private suspend fun getCredentials(): EdamamCredentialsDB {
        return apiCredentialsLocal.getEdamam(prefUtils.edamamCredentials)
    }
}