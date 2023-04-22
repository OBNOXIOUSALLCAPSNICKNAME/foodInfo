package com.example.foodinfo.features.home.interactor

import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.State.Utils.transformData
import com.example.foodinfo.domain.interactor.RecipeMetadataInteractor
import com.example.foodinfo.domain.model.CategoryOfRecipeMetadata
import com.example.foodinfo.features.home.mapper.toVHModel
import com.example.foodinfo.features.home.model.CategoryVHModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class CategoryModelInteractor @Inject constructor(
    private val recipeMetadataInteractor: RecipeMetadataInteractor
) {

    fun getCategories(): Flow<State<List<CategoryVHModel>>> {
        return recipeMetadataInteractor.getCategories().transformData { categories ->
            categories.map(CategoryOfRecipeMetadata::toVHModel)
        }
    }
}