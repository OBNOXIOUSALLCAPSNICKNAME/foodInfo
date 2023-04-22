package com.example.foodinfo.features.category.interactor

import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.State.Utils.transformData
import com.example.foodinfo.domain.interactor.RecipeMetadataInteractor
import com.example.foodinfo.domain.model.LabelOfRecipeMetadata
import com.example.foodinfo.features.category.mapper.toVHModel
import com.example.foodinfo.features.category.model.LabelVHModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class LabelModelInteractor @Inject constructor(
    private val recipeMetadataInteractor: RecipeMetadataInteractor
) {

    fun getCategoryLabels(ID: Int): Flow<State<List<LabelVHModel>>> {
        return recipeMetadataInteractor.getCategoryLabels(ID).transformData { labels ->
            labels.map(LabelOfRecipeMetadata::toVHModel)
        }
    }
}