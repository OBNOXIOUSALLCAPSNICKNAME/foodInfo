package com.example.foodinfo.model.repository.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.foodinfo.model.local.RecipeExtended
import com.example.foodinfo.model.local.RecipeShort
import com.example.foodinfo.model.local.dao.RecipesDAO
import com.example.foodinfo.model.local.entities.SearchFilter
import com.example.foodinfo.model.repository.RepositoryRecipes
import com.example.foodinfo.utils.ResourcesProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class RepositoryRecipesImpl @Inject constructor(
    private val resourcesProvider: ResourcesProvider, private val recipesDAO: RecipesDAO
) : RepositoryRecipes {

    override fun getPopular(): Flow<PagingData<RecipeShort>> {
        return Pager(
            config = DB_POPULAR_PAGER,
            pagingSourceFactory = {
                recipesDAO.getPopular()
            }
        ).flow.map { pagingData ->
            pagingData.map { recipe ->
                delay(100L) // для теста плейсхолдеров и прогресс бара
                RecipeShort.fromDTO(recipe, resourcesProvider)
            }
        }
    }

    override fun getByFilterShort(filter: SearchFilter): Flow<PagingData<RecipeShort>> {
        return Pager(
            config = DB_EXPLORE_PAGER,
            pagingSourceFactory = {
                recipesDAO.getByFilterShort(SimpleSQLiteQuery(filter.query))
            }
        ).flow.map { pagingData ->
            pagingData.map { recipe ->
                delay(100L) // для теста плейсхолдеров и прогресс бара
                RecipeShort.fromDTO(recipe, resourcesProvider)
            }
        }
    }

    override fun getByIdExtended(id: String): Flow<RecipeExtended> {
        return recipesDAO.getByIdExtended(id).map { recipe ->
            RecipeExtended.fromDTO(recipe, resourcesProvider)
        }
    }


    companion object {
        val DB_POPULAR_PAGER = PagingConfig(
            pageSize = 10,
            initialLoadSize = 20,
            jumpThreshold = 40,
            maxSize = 40
        )
        val DB_EXPLORE_PAGER = PagingConfig(
            pageSize = 5,
            initialLoadSize = 5,
            jumpThreshold = 15,
            maxSize = 15
        )
    }
}