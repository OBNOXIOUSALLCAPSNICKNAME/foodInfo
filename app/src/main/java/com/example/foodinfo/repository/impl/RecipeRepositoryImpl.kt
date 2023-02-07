package com.example.foodinfo.repository.impl

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.foodinfo.local.dao.RecipeDAO
import com.example.foodinfo.local.dto.NutrientRecipeAttrDB
import com.example.foodinfo.local.dto.RecipeAttrsDB
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

    override fun getByIdExtended(
        recipeID: String,
        attrs: RecipeAttrsDB
    ): Flow<State<RecipeExtendedModel>> {
        return getData(
            context = context,
            remoteDataProvider = { recipeAPI.getRecipeExtended(recipeID, attrs) },
            localDataFlowProvider = { recipeDAO.getByIdExtended(recipeID) },
            updateLocalDelegate = { recipeDAO.addRecipeExtended(it) },
            mapRemoteToLocalDelegate = { it!!.toDB() },
            mapLocalToModelDelegate = { it.toModelExtended() }
        )
    }

    override fun getByIdNutrients(
        recipeID: String,
        attrs: List<NutrientRecipeAttrDB>
    ): Flow<State<List<NutrientOfRecipeModel>>> {
        return getData(
            context = context,
            remoteDataProvider = { recipeAPI.getNutrients(recipeID, attrs) },
            localDataFlowProvider = { recipeDAO.getNutrients(recipeID) },
            updateLocalDelegate = { recipeDAO.addNutrients(it) },
            mapRemoteToLocalDelegate = { it.map { nutrient -> nutrient.toDB() } },
            mapLocalToModelDelegate = { it.map { nutrient -> nutrient.toModel() } }
        )
    }

    override fun getByIdIngredients(recipeID: String): Flow<State<List<RecipeIngredientModel>>> {
        return getData(
            context = context,
            remoteDataProvider = { recipeAPI.getIngredients(recipeID) },
            localDataFlowProvider = { recipeDAO.getIngredients(recipeID) },
            updateLocalDelegate = { recipeDAO.addIngredients(it) },
            mapRemoteToLocalDelegate = { it.map { ingredient -> ingredient.toDB() } },
            mapLocalToModelDelegate = { it.map { ingredient -> ingredient.toModel() } }
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