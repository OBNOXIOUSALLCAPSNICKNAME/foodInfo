package com.example.foodinfo.features.search_filter.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.model.LabelHint
import com.example.foodinfo.features.search_filter.interactor.CategoryEditInteractor
import com.example.foodinfo.features.search_filter.model.LabelEditVHModel
import com.example.foodinfo.utils.CoroutineLauncher
import com.example.foodinfo.utils.LaunchStrategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


class CategoryOfSearchFilterViewModel @Inject constructor(
    private val categoryEditInteractor: CategoryEditInteractor
) : ViewModel() {

    private val resetCoroutine = CoroutineLauncher(
        viewModelScope, Dispatchers.IO, LaunchStrategy.IGNORE
    )

    private val updateCoroutine = CoroutineLauncher(
        viewModelScope, Dispatchers.IO, LaunchStrategy.IGNORE
    )

    var categoryID: Int = -1

    val categoryLabels: SharedFlow<State<List<LabelEditVHModel>>> by lazy {
        categoryEditInteractor.getCategoryEdit(categoryID).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }


    suspend fun getLabel(ID: Int): LabelHint {
        return categoryEditInteractor.getLabel(ID)
    }

    fun reset() {
        resetCoroutine.launch {
            categoryEditInteractor.resetCategory(categoryID)
        }
    }

    fun updateLabel(ID: Int, isSelected: Boolean) {
        updateCoroutine.launch {
            categoryEditInteractor.updateLabel(ID, isSelected)
        }
    }
}