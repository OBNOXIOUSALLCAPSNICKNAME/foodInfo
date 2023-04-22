package com.example.foodinfo.features.search_filter.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.model.NutrientHint
import com.example.foodinfo.features.search_filter.interactor.NutrientsEditInteractor
import com.example.foodinfo.features.search_filter.model.NutrientEditVHModel
import com.example.foodinfo.utils.CoroutineLauncher
import com.example.foodinfo.utils.LaunchStrategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


class NutrientsOfSearchFilterViewModel @Inject constructor(
    private val nutrientsEditInteractor: NutrientsEditInteractor,
) : ViewModel() {

    private val resetCoroutine = CoroutineLauncher(
        viewModelScope, Dispatchers.IO, LaunchStrategy.IGNORE
    )

    private val updateCoroutine = CoroutineLauncher(
        viewModelScope, Dispatchers.IO, LaunchStrategy.IGNORE
    )

    val nutrients: SharedFlow<State<List<NutrientEditVHModel>>> by lazy {
        nutrientsEditInteractor.getNutrientsEdit().shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }


    suspend fun getNutrient(ID: Int): NutrientHint {
        return nutrientsEditInteractor.getNutrientHint(ID)
    }

    fun reset() {
        resetCoroutine.launch {
            nutrientsEditInteractor.resetNutrients()
        }
    }

    fun updateNutrient(id: Int, minValue: Float?, maxValue: Float?) {
        updateCoroutine.launch {
            nutrientsEditInteractor.updateNutrient(id, minValue, maxValue)
        }
    }
}