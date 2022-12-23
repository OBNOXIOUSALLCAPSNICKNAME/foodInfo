package com.example.foodinfo.repository.impl

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.foodinfo.local.dao.RecipesDAO
import com.example.foodinfo.repository.RepositoryRecipes
import com.example.foodinfo.repository.mapper.toModel
import com.example.foodinfo.repository.mapper.toModelFavorite
import com.example.foodinfo.repository.mapper.toModelRecipe
import com.example.foodinfo.repository.mapper.toModelShort
import com.example.foodinfo.repository.model.*
import com.example.foodinfo.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class RepositoryRecipesImpl @Inject constructor(
    private val context: Context,
    private val recipesDAO: RecipesDAO
) : RepositoryRecipes {

    override fun getPopular(): Flow<PagingData<RecipeShortModel>> {
        return Pager(
            config = DB_POPULAR_PAGER,
            pagingSourceFactory = {
                recipesDAO.getPopular()
            }
        ).flow.map { pagingData -> pagingData.map { it.toModelShort() } }.flowOn(Dispatchers.IO)
    }

    override fun getFavorite(): Flow<PagingData<RecipeFavoriteModel>> {
        return Pager(
            config = DB_FAVORITE_PAGER,
            pagingSourceFactory = {
                recipesDAO.getFavorite()
            }
        ).flow.map { pagingData -> pagingData.map { it.toModelFavorite() } }.flowOn(Dispatchers.IO)
    }

    override fun getFavoriteIds(): List<String> {
        return recipesDAO.getFavoriteIds()
    }

    override fun getByFilter(query: String): Flow<PagingData<RecipeShortModel>> {
        return Pager(
            config = DB_EXPLORE_PAGER,
            pagingSourceFactory = {
                recipesDAO.getByFilter(SimpleSQLiteQuery(query))
            }
        ).flow.map { pagingData -> pagingData.map { it.toModelShort() } }.flowOn(Dispatchers.IO)
    }

    override fun getById(id: String): Flow<State<RecipeModel>> {
        return flow {
            emit(State.Loading())
            recipesDAO.getById(id).collect {
                emit(State.Success(it.toModel()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getByIdIngredients(id: String): Flow<State<List<RecipeIngredientModel>>> {
        return flow {
            emit(State.Loading())
            recipesDAO.getByIdIngredients(id).collect { list ->
                emit(State.Success(list.map { it.toModel() }))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getByIdNutrients(id: String): Flow<State<List<NutrientRecipeModel>>> {
        return flow {
            emit(State.Loading())
            recipesDAO.getByIdNutrients(id).collect { list ->
                emit(State.Success(list.map { it.toModel() }))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getByIdLabels(id: String): Flow<State<List<CategoryRecipeModel>>> {
        return flow {
            emit(State.Loading())
            recipesDAO.getByIdLabels(id).collect {
                emit(State.Success(it.toModelRecipe()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun updateFavoriteMark(id: String) {
        recipesDAO.updateFavoriteStatus(id)
    }

    override fun delFromFavorite(id: List<String>) {
        recipesDAO.delFromFavorite(id)
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