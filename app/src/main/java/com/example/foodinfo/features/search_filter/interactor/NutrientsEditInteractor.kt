package com.example.foodinfo.features.search_filter.interactor

import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.State.Utils.transformData
import com.example.foodinfo.domain.interactor.SearchFilterInteractor
import com.example.foodinfo.domain.model.NutrientHint
import com.example.foodinfo.domain.model.NutrientOfSearchFilter
import com.example.foodinfo.domain.repository.RecipeMetadataRepository
import com.example.foodinfo.domain.repository.SearchFilterRepository
import com.example.foodinfo.features.search_filter.mapper.toVHModelEdit
import com.example.foodinfo.features.search_filter.model.NutrientEditVHModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class NutrientsEditInteractor @Inject constructor(
    private val recipeMetadataRepository: RecipeMetadataRepository,
    private val searchFilterRepository: SearchFilterRepository,
    private val searchFilterInteractor: SearchFilterInteractor
) {

    fun getNutrientsEdit(): Flow<State<List<NutrientEditVHModel>>> {
        return searchFilterInteractor.getNutrients().transformData { nutrients ->
            nutrients.map(NutrientOfSearchFilter::toVHModelEdit)
        }
    }

    suspend fun getNutrientHint(ID: Int): NutrientHint {
        return recipeMetadataRepository.getNutrientHint(ID)
    }

    suspend fun resetNutrients() {
        searchFilterRepository.resetNutrients()
    }

    suspend fun updateNutrient(id: Int, minValue: Float?, maxValue: Float?) {
        searchFilterRepository.updateNutrient(id, minValue, maxValue)
    }
}