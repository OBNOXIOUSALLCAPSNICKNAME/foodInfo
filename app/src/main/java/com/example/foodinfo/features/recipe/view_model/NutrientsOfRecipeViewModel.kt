package com.example.foodinfo.features.recipe.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.model.NutrientHint
import com.example.foodinfo.domain.repository.RecipeMetadataRepository
import com.example.foodinfo.features.recipe.interactor.NutrientModelInteractor
import com.example.foodinfo.features.recipe.model.NutrientVHModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


class NutrientsOfRecipeViewModel @Inject constructor(
    private val nutrientModelInteractor: NutrientModelInteractor,
    private val recipeMetadataRepository: RecipeMetadataRepository
) : ViewModel() {

    var recipeId: String = ""

    val nutrients: SharedFlow<State<List<NutrientVHModel>>> by lazy {
        nutrientModelInteractor.getByIdNutrients(recipeId).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }

    suspend fun getNutrientHint(ID: Int): NutrientHint {
        return recipeMetadataRepository.getNutrientHint(ID)
    }
}