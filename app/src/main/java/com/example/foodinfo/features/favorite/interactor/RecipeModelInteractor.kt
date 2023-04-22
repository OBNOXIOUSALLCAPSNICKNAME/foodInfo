package com.example.foodinfo.features.favorite.interactor

import androidx.paging.PagingData
import androidx.paging.map
import com.example.foodinfo.domain.model.Recipe
import com.example.foodinfo.domain.repository.RecipeRepository
import com.example.foodinfo.features.favorite.mapper.toVHModel
import com.example.foodinfo.features.favorite.model.RecipeVHModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class RecipeModelInteractor @Inject constructor(
    private val recipeRepository: RecipeRepository
) {

    fun getFavorite(): Flow<PagingData<RecipeVHModel>> {
        return recipeRepository.getFavorite().map { pagingData ->
            pagingData.map(Recipe::toVHModel)
        }
    }
}