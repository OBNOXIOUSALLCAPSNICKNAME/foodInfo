package com.example.foodinfo.features.explore.interactor

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.map
import com.example.foodinfo.domain.interactor.RecipeInteractor
import com.example.foodinfo.domain.model.PagingHelper
import com.example.foodinfo.domain.model.Recipe
import com.example.foodinfo.features.explore.mapper.toVHModel
import com.example.foodinfo.features.explore.model.RecipeVHModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class RecipeModelInteractor @Inject constructor(
    private val recipeInteractor: RecipeInteractor,
) : ViewModel() {

    fun getByFilter(pagingHelper: PagingHelper): Flow<PagingData<RecipeVHModel>> {
        return recipeInteractor.getByFilter(pagingHelper).map { pagingData ->
            pagingData.map(Recipe::toVHModel)
        }
    }
}