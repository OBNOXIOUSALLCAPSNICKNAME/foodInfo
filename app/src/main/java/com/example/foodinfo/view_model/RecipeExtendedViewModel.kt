package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.domain.repository.RecipeAttrRepository
import com.example.foodinfo.domain.repository.RecipeRepository
import com.example.foodinfo.domain.model.LabelHintModel
import com.example.foodinfo.domain.model.RecipeExtendedModel
import com.example.foodinfo.domain.state.State
import com.example.foodinfo.domain.use_case.RecipeUseCase
import com.example.foodinfo.utils.CoroutineLauncher
import com.example.foodinfo.utils.LaunchStrategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


class RecipeExtendedViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase,
    private val recipeRepository: RecipeRepository,
    private val recipeAttrRepository: RecipeAttrRepository
) : ViewModel() {

    private val invertCoroutine = CoroutineLauncher(
        viewModelScope, Dispatchers.IO, LaunchStrategy.IGNORE
    )

    var recipeId: String = ""

    val recipe: SharedFlow<State<RecipeExtendedModel>> by lazy {
        recipeUseCase.getByIdExtended(recipeId).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }

    suspend fun getLabel(ID: Int): LabelHintModel {
        return recipeAttrRepository.getLabelHint(ID)
    }

    fun invertFavoriteStatus() {
        invertCoroutine.launch {
            recipeRepository.invertFavoriteStatus(recipeId)
        }
    }
}