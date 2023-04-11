package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.domain.repository.RecipeAttrRepository
import com.example.foodinfo.domain.model.NutrientHintModel
import com.example.foodinfo.domain.model.NutrientOfRecipeModel
import com.example.foodinfo.domain.state.State
import com.example.foodinfo.domain.use_case.RecipeUseCase
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


class RecipeNutrientsViewModel @Inject constructor(
    recipeUseCase: RecipeUseCase,
    private val recipeAttrRepository: RecipeAttrRepository
) :
    ViewModel() {

    var recipeId: String = ""

    val nutrients: SharedFlow<State<List<NutrientOfRecipeModel>>> by lazy {
        recipeUseCase.getByIdNutrients(recipeId).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }

    suspend fun getNutrient(ID: Int): NutrientHintModel {
        return recipeAttrRepository.getNutrientHint(ID)
    }
}