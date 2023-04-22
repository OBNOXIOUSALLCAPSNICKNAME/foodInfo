package com.example.foodinfo.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.foodinfo.data.local.data_source.RecipeLocalSource
import com.example.foodinfo.data.local.model.IngredientOfRecipeDB
import com.example.foodinfo.data.local.model.NutrientOfRecipeExtendedDB
import com.example.foodinfo.data.local.model.RecipeDB
import com.example.foodinfo.data.local.model.RecipeExtendedDB
import com.example.foodinfo.data.mapper.*
import com.example.foodinfo.data.remote.data_source.RecipeRemoteSource
import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.model.*
import com.example.foodinfo.domain.repository.RecipeRepository
import com.example.foodinfo.utils.*
import com.example.foodinfo.utils.paging.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RecipeRepositoryImpl @Inject constructor(
    private val recipeLocal: RecipeLocalSource,
    private val recipeRemote: RecipeRemoteSource
) : BaseRepository(), RecipeRepository {

    override fun getFavorite(): Flow<PagingData<Recipe>> {
        return Pager(
            config = AppPagingConfig.RECIPE_FAVORITE_PAGER,
            pagingSourceFactory = {
                MapPagingSource(
                    originalSource = recipeLocal.getFavorite(),
                    mapperDelegate = RecipeDB::toModel
                )
            }
        ).flow
    }

    override suspend fun getFavoriteIDs(): Set<String> {
        return recipeLocal.getFavoriteIds().toSet()
    }

    override fun getFavoriteCount(): Flow<Int> {
        return recipeLocal.getFavoriteCount()
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getByFilter(
        apiCredentials: EdamamCredentials,
        pagingHelper: PagingHelper
    ): Flow<PagingData<Recipe>> {
        return Pager(
            config = pagingHelper.pagingConfig,
            remoteMediator = EdamamRemoteMediator(
                remoteDataProvider = { pageURL ->
                    if (pageURL == "") {
                        recipeRemote.getInitPage(
                            apiCredentials = apiCredentials,
                            filterPreset = pagingHelper.filterPreset,
                            inputText = pagingHelper.inputText,
                        )
                    } else {
                        recipeRemote.getNextPage(pageURL)
                    }
                },
                saveRemoteDelegate = recipeLocal::addRecipes,
                mapToLocalDelegate = { recipeHits ->
                    recipeHits.map { recipeHit -> recipeHit.recipe.toDBSave(pagingHelper.recipeMetadata) }
                }
            ),
            pagingSourceFactory = {
                MapPagingSource(
                    originalSource = recipeLocal.getByFilter(
                        filterPreset = pagingHelper.filterPreset,
                        inputText = pagingHelper.inputText,
                        sessionStartTime = pagingHelper.sessionStartTime
                    ),
                    mapperDelegate = RecipeDB::toModel
                )
            }
        ).flow
    }

    override fun getByIdExtended(
        apiCredentials: EdamamCredentials,
        metadata: RecipeMetadata,
        recipeID: String
    ): Flow<State<RecipeExtended>> {
        return getData(
            remoteDataProvider = {
                DataSource.Remote(recipeRemote.getRecipe(apiCredentials, recipeID))
            },
            localDataProvider = { DataSource.LocalFlow(recipeLocal.getByIdExtended(recipeID)) },
            saveRemoteDelegate = recipeLocal::addRecipe,
            mapToLocalDelegate = { recipeHit -> recipeHit.recipe.toDBSave(metadata) },
            mapToModelDelegate = RecipeExtendedDB::toModelExtended
        )
    }

    override fun getByIdNutrients(
        apiCredentials: EdamamCredentials,
        metadata: List<NutrientOfRecipeMetadata>,
        recipeID: String
    ): Flow<State<List<NutrientOfRecipe>>> {
        return getData(
            remoteDataProvider = {
                DataSource.Remote(recipeRemote.getRecipeNutrients(apiCredentials, recipeID))
            },
            localDataProvider = { DataSource.LocalFlow(recipeLocal.getNutrients(recipeID)) },
            saveRemoteDelegate = recipeLocal::addNutrients,
            mapToLocalDelegate = { nutrients -> nutrients.toDB(recipeID, metadata) },
            mapToModelDelegate = { nutrients -> nutrients.map(NutrientOfRecipeExtendedDB::toModel) }
        )
    }

    override fun getByIdIngredients(
        apiCredentials: EdamamCredentials,
        recipeID: String
    ): Flow<State<List<IngredientOfRecipe>>> {
        return getData(
            remoteDataProvider = {
                DataSource.Remote(recipeRemote.getRecipeIngredients(apiCredentials, recipeID))
            },
            localDataProvider = { DataSource.LocalFlow(recipeLocal.getIngredients(recipeID)) },
            saveRemoteDelegate = recipeLocal::addIngredients,
            mapToLocalDelegate = { ingredients -> ingredients.map { ingredient -> ingredient.toDB(recipeID) } },
            mapToModelDelegate = { ingredients -> ingredients.map(IngredientOfRecipeDB::toModel) }
        )
    }

    override suspend fun invertFavoriteStatus(ID: String) {
        recipeLocal.invertFavoriteStatus(ID)
    }

    override suspend fun delFromFavorite(IDs: List<String>) {
        recipeLocal.delFromFavorite(IDs)
    }
}