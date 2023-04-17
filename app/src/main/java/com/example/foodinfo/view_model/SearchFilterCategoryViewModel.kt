package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.domain.model.CategoryOfSearchFilterEditModel
import com.example.foodinfo.domain.model.LabelHintModel
import com.example.foodinfo.domain.repository.RecipeAttrRepository
import com.example.foodinfo.domain.repository.SearchFilterRepository
import com.example.foodinfo.domain.state.State
import com.example.foodinfo.domain.use_case.SearchFilterUseCase
import com.example.foodinfo.utils.CoroutineLauncher
import com.example.foodinfo.utils.LaunchStrategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


class SearchFilterCategoryViewModel @Inject constructor(
    private val recipeAttrRepository: RecipeAttrRepository,
    private val searchFilterRepository: SearchFilterRepository,
    private val searchFilterUseCase: SearchFilterUseCase,
) : ViewModel() {

    private val resetCoroutine = CoroutineLauncher(
        viewModelScope, Dispatchers.IO, LaunchStrategy.IGNORE
    )

    private val updateCoroutine = CoroutineLauncher(
        viewModelScope, Dispatchers.IO, LaunchStrategy.IGNORE
    )

    var categoryID: Int = -1

    val category: SharedFlow<State<CategoryOfSearchFilterEditModel>> by lazy {
        searchFilterUseCase.getCategoryEdit(categoryID = categoryID).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }


    suspend fun getLabelHint(ID: Int): LabelHintModel {
        return recipeAttrRepository.getLabelHint(ID)
    }

    fun reset() {
        resetCoroutine.launch {
            searchFilterRepository.resetCategory(categoryID)
        }
    }

    fun update(id: Int, isSelected: Boolean) {
        updateCoroutine.launch {
            searchFilterRepository.updateLabel(id, isSelected)
        }
    }
}