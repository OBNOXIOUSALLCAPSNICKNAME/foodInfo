package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.repository.RecipeAttrRepository
import com.example.foodinfo.repository.model.CategorySearchModel
import com.example.foodinfo.repository.model.LabelSearchModel
import com.example.foodinfo.utils.State
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


class SearchCategoryViewModel @Inject constructor(
    private val recipeAttrRepository: RecipeAttrRepository
) : ViewModel() {

    var categoryID: Int = 0

    val labels: SharedFlow<State<List<LabelSearchModel>>> by lazy {
        recipeAttrRepository.getLabelsSearch(categoryID).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }

    val category: SharedFlow<State<CategorySearchModel>> by lazy {
        recipeAttrRepository.getCategory(categoryID).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }
}