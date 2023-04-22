package com.example.foodinfo.features.recipe.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.domain.State
import com.example.foodinfo.features.recipe.interactor.IngredientModelInteractor
import com.example.foodinfo.features.recipe.model.IngredientVHModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


class IngredientsOfRecipeViewModel @Inject constructor(ingredientModelInteractor: IngredientModelInteractor) :
    ViewModel() {

    var recipeID: String = ""

    val ingredients: SharedFlow<State<List<IngredientVHModel>>> by lazy {
        ingredientModelInteractor.getByIdIngredients(recipeID).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }
}