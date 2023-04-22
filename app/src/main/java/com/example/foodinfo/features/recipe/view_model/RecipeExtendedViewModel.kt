package com.example.foodinfo.features.recipe.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.model.LabelHint
import com.example.foodinfo.domain.repository.RecipeMetadataRepository
import com.example.foodinfo.domain.repository.RecipeRepository
import com.example.foodinfo.features.recipe.interactor.RecipeModelInteractor
import com.example.foodinfo.features.recipe.model.RecipeModel
import com.example.foodinfo.utils.CoroutineLauncher
import com.example.foodinfo.utils.LaunchStrategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


class RecipeExtendedViewModel @Inject constructor(
    private val recipeModelInteractor: RecipeModelInteractor,
    private val recipeMetadataRepository: RecipeMetadataRepository,
    private val recipeRepository: RecipeRepository,
) : ViewModel() {

    private val invertCoroutine = CoroutineLauncher(
        viewModelScope, Dispatchers.IO, LaunchStrategy.IGNORE
    )

    var recipeID: String = ""

    val recipe: SharedFlow<State<RecipeModel>> by lazy {
        recipeModelInteractor.getByIdExtended(recipeID).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }

    suspend fun getLabelHint(ID: Int): LabelHint {
        return recipeMetadataRepository.getLabelHint(ID)
    }

    fun invertFavoriteStatus() {
        invertCoroutine.launch {
            recipeRepository.invertFavoriteStatus(recipeID)
        }
    }
}