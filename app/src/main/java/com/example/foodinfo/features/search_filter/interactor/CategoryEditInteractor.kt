package com.example.foodinfo.features.search_filter.interactor

import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.State.Utils.transformData
import com.example.foodinfo.domain.interactor.SearchFilterInteractor
import com.example.foodinfo.domain.model.LabelHint
import com.example.foodinfo.domain.model.LabelOfSearchFilter
import com.example.foodinfo.domain.repository.RecipeMetadataRepository
import com.example.foodinfo.domain.repository.SearchFilterRepository
import com.example.foodinfo.features.search_filter.mapper.toModelEdit
import com.example.foodinfo.features.search_filter.model.LabelEditVHModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class CategoryEditInteractor @Inject constructor(
    private val recipeMetadataRepository: RecipeMetadataRepository,
    private val searchFilterRepository: SearchFilterRepository,
    private val searchFilterInteractor: SearchFilterInteractor,
) {

    fun getCategoryEdit(categoryID: Int): Flow<State<List<LabelEditVHModel>>> {
        return searchFilterInteractor.getCategory(categoryID).transformData { category ->
            category.labels.map(LabelOfSearchFilter::toModelEdit)
        }
    }

    suspend fun getLabelHint(ID: Int): LabelHint {
        return recipeMetadataRepository.getLabelHint(ID)
    }

    suspend fun resetCategory(categoryID: Int) {
        searchFilterRepository.resetCategory(categoryID)
    }

    suspend fun updateLabel(ID: Int, isSelected: Boolean) {
        searchFilterRepository.updateLabel(ID, isSelected)
    }
}