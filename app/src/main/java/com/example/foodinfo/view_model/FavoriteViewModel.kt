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

    // not sure about making database query every time but if simply initialize
    // totalRecipesCount once at viewModel initialization will cause to incorrect behavior
    // in situation when user removes a recipe from favorite in RecipeExtended screen
    // which will cause favorite recipes table size to change and totalRecipesCount
    // to store incorrect value
    // there are couple ways to avoid that:
    // use flow in getFavoriteIds (but have to store whole list of recipe ids here)
    // make function in viewModel for updating totalRecipesCount value and call it
    // on fragment's onStart() (but forcing View layer to care about such stuff is kinda
    // breaking the concept of having separate ViewModel/Model layers)
    val totalRecipesCount: Int
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