package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.domain.repository.RecipeAttrRepository
import com.example.foodinfo.domain.repository.SearchFilterRepository
import com.example.foodinfo.domain.model.NutrientHintModel
import com.example.foodinfo.domain.model.NutrientOfSearchFilterEditModel
import com.example.foodinfo.domain.state.State
import com.example.foodinfo.domain.use_case.SearchFilterUseCase
import com.example.foodinfo.utils.CoroutineLauncher
import com.example.foodinfo.utils.LaunchStrategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


class SearchFilterNutrientsViewModel @Inject constructor(
    private val recipeAttrRepository: RecipeAttrRepository,
    private val searchFilterRepository: SearchFilterRepository,
    private val searchFilterUseCase: SearchFilterUseCase,
) : ViewModel() {

    private val resetCoroutine = CoroutineLauncher(
        viewModelScope, Dispatchers.IO, LaunchStrategy.IGNORE
    )

    private val updateCoroutine = CoroutineLauncher(
        viewModelScope, Dispatchers.IO, LaunchStrategy.IGNORE
    )

    val nutrients: SharedFlow<State<List<NutrientOfSearchFilterEditModel>>> by lazy {
        searchFilterUseCase.getNutrientsEdit().shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }


    suspend fun getNutrient(ID: Int): NutrientHintModel {
        return recipeAttrRepository.getNutrientHint(ID)
    }

    fun reset() {
        resetCoroutine.launch {
            searchFilterRepository.resetNutrients()
        }
    }

    fun update(id: Int, minValue: Float?, maxValue: Float?) {
        updateCoroutine.launch {
            searchFilterRepository.updateNutrient(id, minValue, maxValue)
        }
    }
}