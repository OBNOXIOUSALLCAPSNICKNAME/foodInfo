package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.repository.RecipeAttrRepository
import com.example.foodinfo.repository.model.CategorySearchModel
import com.example.foodinfo.repository.model.LabelSearchModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SearchCategoryViewModel @Inject constructor(
    private val recipeAttrRepository: RecipeAttrRepository
) : ViewModel() {

    var categoryID: Int = 0
        set(initializer) {
            if (initializer == field) {
                return
            }
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    _labels.emit(recipeAttrRepository.getLabelsSearch(initializer))
                }
            }
            field = initializer
        }

    private val _labels = MutableSharedFlow<List<LabelSearchModel>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val labels: SharedFlow<List<LabelSearchModel>> = _labels.shareIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        0
    )

    val category: CategorySearchModel by lazy {
        recipeAttrRepository.getCategory(categoryID)
    }
}