package com.example.foodinfo.repository.impl

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.foodinfo.local.dao.RecipeDAO
import com.example.foodinfo.remote.api.RecipeAPI
import com.example.foodinfo.repository.RecipeRepository
import com.example.foodinfo.repository.mapper.*
import com.example.foodinfo.repository.model.*
import com.example.foodinfo.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class RecipeRepositoryImpl @Inject constructor(
    private val context: Context,
    private val recipeDAO: RecipeDAO,
    private val recipeAPI: RecipeAPI,
) : RecipeRepository() {

    override fun getPopular(): Flow<PagingData<RecipeShortModel>> {
        return Pager(
            config = DB_POPULAR_PAGER,
            pagingSourceFactory = {
                recipeDAO.getPopular()
            }
        ).flow.map { pagingData -> pagingData.map { it.toModelShort() } }.flowOn(Dispatchers.IO)
    }

    override fun getFavorite(): Flow<PagingData<RecipeFavoriteModel>> {
        return Pager(
            config = DB_FAVORITE_PAGER,
            pagingSourceFactory = {
                recipeDAO.getFavorite()
            }
        ).flow.map { pagingData -> pagingData.map { it.toModelFavorite() } }.flowOn(Dispatchers.IO)
    }

    override fun getFavoriteIds(): List<String> {
        return recipeDAO.getFavoriteIds()
    }

    override fun getByFilter(query: String): Flow<PagingData<RecipeShortModel>> {
        return Pager(
            config = DB_EXPLORE_PAGER,
            pagingSourceFactory = {
                recipeDAO.getByFilter(SimpleSQLiteQuery(query))
            }
        ).flow.map { pagingData -> pagingData.map { it.toModelShort() } }.flowOn(Dispatchers.IO)
    }

    override fun getByIdExtended(recipeID: String): Flow<State<RecipeExtendedModel>> {
        return getLatest(
            context = context,
            fetchLocalDelegate = { recipeDAO.getByIdExtended(recipeID) },
            fetchRemoteDelegate = { recipeAPI.getRecipeExtended(recipeID) },
            updateLocalDelegate = { recipeDAO.addRecipeExtended(it) },
            mapRemoteToLocalDelegate = { it!!.toDB() },
            mapLocalToModelDelegate = { it.toModelExtended() }
        )
    }

    override fun getByIdIngredients(recipeID: String): Flow<State<List<RecipeIngredientModel>>> {
        return getLatest(
            context = context,
            fetchLocalDelegate = { recipeDAO.getIngredients(recipeID) },
            fetchRemoteDelegate = { recipeAPI.getIngredients(recipeID) },
            updateLocalDelegate = { recipeDAO.addIngredients(it) },
            mapRemoteToLocalDelegate = { it.map { ingredient -> ingredient.toDB() } },
            mapLocalToModelDelegate = { it.map { ingredient -> ingredient.toModel() } }
        )
    }

    override fun getByIdNutrients(recipeID: String): Flow<State<List<NutrientOfRecipeModel>>> {
        return getLatest(
            context = context,
            fetchLocalDelegate = { recipeDAO.getNutrients(recipeID) },
            fetchRemoteDelegate = { recipeAPI.getNutrients(recipeID) },
            updateLocalDelegate = { recipeDAO.addNutrients(it) },
            mapRemoteToLocalDelegate = { it.map { nutrient -> nutrient.toDB() } },
            mapLocalToModelDelegate = { it.map { nutrient -> nutrient.toModel() } }
        )
    }

    override fun getByIdLabels(recipeID: String): Flow<State<List<CategoryOfRecipeModel>>> {
        return getLatest(
            context = context,
            fetchLocalDelegate = { recipeDAO.getLabels(recipeID) },
            fetchRemoteDelegate = { recipeAPI.getLabels(recipeID) },
            updateLocalDelegate = { recipeDAO.addLabels(it) },
            mapRemoteToLocalDelegate = { it.map { label -> label.toDB() } },
            mapLocalToModelDelegate = { it.toModelRecipe() }
        )
    }

    override fun invertFavoriteStatus(ID: String) {
        recipeDAO.invertFavoriteStatus(ID)
    }

    override fun delFromFavorite(ID: List<String>) {
        recipeDAO.delFromFavorite(ID)
    }


    // definitely this is the wrong place to store pager configs but dunno where else
    companion object {
        val DB_POPULAR_PAGER = PagingConfig(
            pageSize = 10,
            initialLoadSize = 20,
            jumpThreshold = 40,
            maxSize = 40
        )
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
}