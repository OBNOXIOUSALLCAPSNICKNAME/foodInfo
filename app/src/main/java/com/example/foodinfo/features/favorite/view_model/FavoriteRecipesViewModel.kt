package com.example.foodinfo.features.favorite.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.core.utils.CoroutineLauncher
import com.example.foodinfo.core.utils.LaunchStrategy
import com.example.foodinfo.core.utils.view_model.SelectManager
import com.example.foodinfo.domain.repository.RecipeRepository
import com.example.foodinfo.features.favorite.interactor.RecipeModelInteractor
import com.example.foodinfo.features.favorite.model.RecipeVHModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


class FavoriteRecipesViewModel @Inject constructor(
    recipeModelInteractor: RecipeModelInteractor,
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val deleteCoroutine = CoroutineLauncher(
        viewModelScope, Dispatchers.IO, LaunchStrategy.IGNORE
    )

    fun delRecipesFromFavorite() {
        deleteCoroutine.launch {
            recipeRepository.delFromFavorite(selectManager.getSelected())
            selectManager.isSelectMode = false
        }
    }

    val selectManager = SelectManager(
        scope = viewModelScope,
        dataSource = recipeModelInteractor.getFavorite(),
        totalCount = recipeRepository.getFavoriteCount(),
        getAllKeys = recipeRepository::getFavoriteIDs,
        copyItem = RecipeVHModel::copy
    )
}