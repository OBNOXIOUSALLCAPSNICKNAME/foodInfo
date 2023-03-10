package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.repository.RecipeAttrRepository
import com.example.foodinfo.repository.SearchFilterRepository
import com.example.foodinfo.repository.model.CategoryOfSearchFilterEditModel
import com.example.foodinfo.repository.model.LabelHintModel
import com.example.foodinfo.repository.use_case.SearchFilterUseCase
import com.example.foodinfo.utils.State
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


class SearchFilterCategoryViewModel @Inject constructor(
    private val recipeAttrRepository: RecipeAttrRepository,
    private val searchFilterRepository: SearchFilterRepository,
    private val searchFilterUseCase: SearchFilterUseCase,
) : ViewModel() {

    var categoryID: Int = -1

    val category: SharedFlow<State<CategoryOfSearchFilterEditModel>> by lazy {
        searchFilterUseCase.getCategoryEdit(categoryID = categoryID).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }


    fun getLabelHint(ID: Int): LabelHintModel {
        return recipeAttrRepository.getLabelHint(ID)
    }

    fun reset() {
        searchFilterRepository.resetCategory(categoryID)
    }

    fun update(id: Int, isSelected: Boolean) {
        searchFilterRepository.updateLabel(id, isSelected)
    }
}