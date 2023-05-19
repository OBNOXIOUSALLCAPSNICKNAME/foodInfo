package com.example.foodinfo.features.search_filter.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.core.utils.CoroutineLauncher
import com.example.foodinfo.core.utils.LaunchStrategy
import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.model.LabelHint
import com.example.foodinfo.features.search_filter.interactor.CategoryEditInteractor
import com.example.foodinfo.features.search_filter.model.LabelEditVHModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
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


    private var shouldSelect = false

    private val _isAllSelected = MutableSharedFlow<Boolean>()

    val isAllSelected: SharedFlow<Boolean> = _isAllSelected.asSharedFlow()


    var categoryID: Int = -1

    val categoryLabels: SharedFlow<State<List<LabelEditVHModel>>> by lazy {
        categoryEditInteractor.getCategoryEdit(categoryID).transform { state ->
            state.data?.let { labels ->
                labels.all { it.isSelected }.also { allSelected ->
                    _isAllSelected.emit(allSelected)
                    shouldSelect = !allSelected
                }
            }
            emit(state)
        }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), 1)
    }


    suspend fun getLabelHint(ID: Int): LabelHint {
        return categoryEditInteractor.getLabelHint(ID)
    }

    fun toggleAll() {
        resetCoroutine.launch {
            if (shouldSelect) {
                categoryEditInteractor.selectAll(categoryID)

            } else {
                categoryEditInteractor.unselectAll(categoryID)
            }
        }
    }

    fun updateLabel(ID: Int, isSelected: Boolean) {
        updateCoroutine.launch {
            categoryEditInteractor.updateLabel(ID, isSelected)
        }
    }
}