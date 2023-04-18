package com.example.foodinfo.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.foodinfo.data.local.data_source.RecipeLocalSource
import com.example.foodinfo.data.mapper.*
import com.example.foodinfo.data.remote.data_source.RecipeRemoteSource
import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.model.*
import com.example.foodinfo.domain.model.IngredientOfRecipe
import com.example.foodinfo.domain.model.NutrientOfRecipe
import com.example.foodinfo.domain.model.Recipe
import com.example.foodinfo.domain.model.RecipeExtended
import com.example.foodinfo.domain.model.NutrientRecipeAttr
import com.example.foodinfo.domain.model.RecipeAttrs
import com.example.foodinfo.domain.model.SearchFilterPreset
import com.example.foodinfo.domain.repository.RecipeRepository
import com.example.foodinfo.utils.*
import com.example.foodinfo.utils.edamam.FieldSet
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
                    mapperDelegate = { it.toModel() }
                )
            }
        ).flow
    }

    override suspend fun getFavoriteIds(): Set<String> {
        return recipeLocal.getFavoriteIds().toSet()
    }

    override fun getFavoriteCount(): Flow<Int> {
        return recipeLocal.getFavoriteCount()
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getByFilter(
        apiCredentials: EdamamCredentials,
        pagingConfig: PagingConfig,
        filterPreset: SearchFilterPreset,
        recipeAttrs: RecipeAttrs,
        inputText: String,
        isOnline: Boolean
    ): Flow<PagingData<Recipe>> {
        return Pager(
            config = pagingConfig,
            remoteMediator = EdamamRemoteMediator(
                remoteDataProvider = { pageURL ->
                    if (pageURL == "") {
                        recipeRemote.getInitPage(
                            apiCredentials = apiCredentials,
                            filterPreset = filterPreset,
                            inputText = inputText,
                            fieldSet = FieldSet.FULL,
                        )
                    } else {
                        recipeRemote.getNextPage(pageURL)
                    }
                },
                saveRemoteDelegate = { recipes -> recipeLocal.addRecipes(recipes) },
                mapToLocalDelegate = { hits -> hits.map { it.recipe.toDBSave(recipeAttrs) } }
            ),
            pagingSourceFactory = {
                MapPagingSource(
                    originalSource = recipeLocal.getByFilter(
                        filterPreset = filterPreset,
                        inputText = inputText,
                        isOnline = isOnline
                    ),
                    mapperDelegate = { it.toModel() }
                )
            }
        ).flow
    }

    override fun getByIdExtended(
        apiCredentials: EdamamCredentials,
        recipeID: String,
        attrs: RecipeAttrs
    ): Flow<State<RecipeExtended>> {
        return getData(
            remoteDataProvider = {
                DataSource.Remote(recipeRemote.getRecipe(apiCredentials, FieldSet.FULL, recipeID))
            },
            localDataProvider = { DataSource.LocalFlow(recipeLocal.getByIdExtended(recipeID)) },
            saveRemoteDelegate = { recipeLocal.addRecipe(it) },
            mapToLocalDelegate = { it.recipe.toDBSave(attrs) },
            mapToModelDelegate = { it.toModelExtended() }
        )
    }

    override fun getByIdNutrients(
        apiCredentials: EdamamCredentials,
        recipeID: String,
        attrs: List<NutrientRecipeAttr>
    ): Flow<State<List<NutrientOfRecipe>>> {
        return getData(
            remoteDataProvider = {
                DataSource.Remote(recipeRemote.getRecipe(apiCredentials, FieldSet.NUTRIENTS, recipeID))
            },
            localDataProvider = { DataSource.LocalFlow(recipeLocal.getNutrients(recipeID)) },
            saveRemoteDelegate = { recipeLocal.addNutrients(it) },
            mapToLocalDelegate = { it.recipe.nutrients!!.toDB(recipeID, attrs) },
            mapToModelDelegate = { it.map { nutrient -> nutrient.toModel() } }
        )
    }

    override fun getByIdIngredients(
        apiCredentials: EdamamCredentials,
        recipeID: String
    ): Flow<State<List<IngredientOfRecipe>>> {
        return getData(
            remoteDataProvider = {
                DataSource.Remote(recipeRemote.getRecipe(apiCredentials, FieldSet.INGREDIENTS, recipeID))
            },
            localDataProvider = { DataSource.LocalFlow(recipeLocal.getIngredients(recipeID)) },
            saveRemoteDelegate = { recipeLocal.addIngredients(it) },
            mapToLocalDelegate = { it.recipe.ingredients!!.map { ingredient -> ingredient.toDB(recipeID) } },
            mapToModelDelegate = { it.map { ingredient -> ingredient.toModel() } }
        )
    }

    override suspend fun invertFavoriteStatus(ID: String) {
        recipeLocal.invertFavoriteStatus(ID)
    }

    override suspend fun delFromFavorite(ID: List<String>) {
        recipeLocal.delFromFavorite(ID)
    }
}