package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.repository.RecipeAttrRepository
import com.example.foodinfo.repository.SearchFilterRepository
import com.example.foodinfo.repository.model.NutrientHintModel
import com.example.foodinfo.repository.model.NutrientOfSearchFilterEditModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


class SearchFilterNutrientsViewModel @Inject constructor(
    private val recipeAttrRepository: RecipeAttrRepository,
    private val searchFilterRepository: SearchFilterRepository,
) : ViewModel() {

    val nutrients: SharedFlow<List<NutrientOfSearchFilterEditModel>> by lazy {
        searchFilterRepository.getNutrientsEdit().shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }


    fun getNutrient(ID: Int): NutrientHintModel {
        return recipeAttrRepository.getNutrientHint(ID)
    }

    fun reset() {
        searchFilterRepository.resetNutrients()
    }

    fun updateField(id: Int, minValue: Float, maxValue: Float) {
        searchFilterRepository.updateNutrient(id, minValue, maxValue)
    }
}