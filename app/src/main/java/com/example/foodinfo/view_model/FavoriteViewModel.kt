package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.foodinfo.repository.RecipeRepository
import com.example.foodinfo.repository.model.RecipeFavoriteModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class FavoriteViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _isEditMode = MutableStateFlow(false)
    val isEditMode: StateFlow<Boolean> = _isEditMode.asStateFlow()

    private val _selectedCount = MutableStateFlow(0)
    val selectedCount: StateFlow<Int> = _selectedCount.asStateFlow()

    private val selectedRecipes: HashSet<String> = hashSetOf()

    val totalRecipesCount: Int // TODO replace with GET COUNT
        get() = recipeRepository.getFavoriteIds().size

    val recipes: StateFlow<PagingData<RecipeFavoriteModel>> =
        recipeRepository.getFavorite()
            .cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())


    fun setEditMode(isEdit: Boolean) {
        _isEditMode.value = isEdit
    }

    fun isSelected(id: String): Boolean {
        return id in selectedRecipes
    }

    fun delRecipesFromFavorite() {
        recipeRepository.delFromFavorite(selectedRecipes.toList())
    }

    fun updateSelectStatus(id: String) {
        if (id in selectedRecipes) {
            selectedRecipes.remove(id)
        } else {
            selectedRecipes.add(id)
        }
        _selectedCount.value = selectedRecipes.size
    }

    fun selectAll() {
        selectedRecipes.addAll(recipeRepository.getFavoriteIds())
        _selectedCount.value = selectedRecipes.size
    }

    fun unselectAll() {
        selectedRecipes.clear()
        _selectedCount.value = selectedRecipes.size
    }
}