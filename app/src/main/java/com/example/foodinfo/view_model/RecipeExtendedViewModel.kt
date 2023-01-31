package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.repository.RecipeAttrRepository
import com.example.foodinfo.repository.RecipeRepository
import com.example.foodinfo.repository.model.LabelHintModel
import com.example.foodinfo.repository.model.RecipeExtendedModel
import com.example.foodinfo.utils.State
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


class RecipeExtendedViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val recipeAttrRepository: RecipeAttrRepository
) :
    ViewModel() {

    var recipeId: String = ""

    val recipe: SharedFlow<State<RecipeExtendedModel>> by lazy {
        recipeRepository.getByIdExtended(recipeId).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }

    fun getLabel(ID: Int): LabelHintModel {
        return recipeAttrRepository.getLabelHint(ID)
    }

    fun invertFavoriteStatus() {
        recipeRepository.invertFavoriteStatus(recipeId)
    }
}