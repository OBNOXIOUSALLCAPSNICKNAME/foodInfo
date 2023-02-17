package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.repository.RecipeAttrRepository
import com.example.foodinfo.repository.model.CategoryTargetSearchModel
import com.example.foodinfo.utils.State
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


class SearchCategoryViewModel @Inject constructor(
    private val recipeAttrRepository: RecipeAttrRepository
) : ViewModel() {

    var categoryID: Int = 0

    val category: SharedFlow<State<CategoryTargetSearchModel>> by lazy {
        recipeAttrRepository.getCategoryLabelsLatest(categoryID).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }
}