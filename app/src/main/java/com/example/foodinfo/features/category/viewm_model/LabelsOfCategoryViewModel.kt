package com.example.foodinfo.features.category.viewm_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.domain.State
import com.example.foodinfo.features.category.interactor.LabelModelInteractor
import com.example.foodinfo.features.category.model.LabelVHModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


class LabelsOfCategoryViewModel @Inject constructor(
    private val labelModelInteractor: LabelModelInteractor
) : ViewModel() {

    var categoryID: Int = 0

    val categoryLabels: SharedFlow<State<List<LabelVHModel>>> by lazy {
        labelModelInteractor.getCategoryLabels(categoryID).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }
}