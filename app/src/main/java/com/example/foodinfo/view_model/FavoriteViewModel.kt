package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.repository.RecipeRepository
import com.example.foodinfo.utils.CoroutineLauncher
import com.example.foodinfo.utils.LaunchStrategy
import com.example.foodinfo.utils.view_model.SelectManager
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


class FavoriteViewModel @Inject constructor(
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
        dataSource = recipeRepository.getFavorite(),
        totalCount = recipeRepository.getFavoriteCount(),
        getItemKey = { it.ID },
        getAllKeys = recipeRepository::getFavoriteIds
    )
}

