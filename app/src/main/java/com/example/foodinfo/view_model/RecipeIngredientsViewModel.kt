package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.repository.RecipeRepository
import com.example.foodinfo.repository.model.RecipeIngredientModel
import com.example.foodinfo.utils.State
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


class RecipeIngredientsViewModel @Inject constructor(recipeRepository: RecipeRepository) :
    ViewModel() {

    var recipeId: String = ""

    val ingredients: SharedFlow<State<List<RecipeIngredientModel>>> by lazy {
        recipeRepository.getByIdIngredients(recipeId).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }
}