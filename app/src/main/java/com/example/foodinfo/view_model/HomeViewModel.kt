package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.domain.repository.RecipeAttrRepository
import com.example.foodinfo.domain.model.CategorySearchModel
import com.example.foodinfo.domain.state.State
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    recipeAttrRepository: RecipeAttrRepository
) : ViewModel() {

    val categories: SharedFlow<State<List<CategorySearchModel>>> by lazy {
        recipeAttrRepository.getCategoriesLatest().shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }
}