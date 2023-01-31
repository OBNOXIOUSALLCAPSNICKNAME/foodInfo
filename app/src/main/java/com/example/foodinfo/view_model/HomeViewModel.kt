package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.repository.RecipeAttrRepository
import com.example.foodinfo.repository.model.CategorySearchModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    recipeAttrRepository: RecipeAttrRepository
) : ViewModel() {

    val categories: SharedFlow<List<CategorySearchModel>> = flow {
        emit(recipeAttrRepository.getCategories())
    }.flowOn(Dispatchers.IO).shareIn(
        viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
    )
}