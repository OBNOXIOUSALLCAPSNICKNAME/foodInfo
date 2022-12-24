package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.repository.RepositoryRecipeFieldsInfo
import com.example.foodinfo.repository.RepositoryRecipes
import com.example.foodinfo.repository.model.LabelHintModel
import com.example.foodinfo.repository.model.RecipeExtendedModel
import com.example.foodinfo.utils.State
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


class RecipeExtendedViewModel @Inject constructor(
    private val repositoryRecipes: RepositoryRecipes,
    private val repositoryRecipeFieldsInfo: RepositoryRecipeFieldsInfo
) :
    ViewModel() {

    var recipeId: String = ""

    val recipe: SharedFlow<State<RecipeExtendedModel>> by lazy {
        repositoryRecipes.getByIdExtended(recipeId).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }

    fun getLabel(category: String, label: String): LabelHintModel {
        return repositoryRecipeFieldsInfo.getLabelHint(category, label)
    }

    fun updateFavoriteMark() {
        repositoryRecipes.updateFavoriteMark(recipeId)
    }
}