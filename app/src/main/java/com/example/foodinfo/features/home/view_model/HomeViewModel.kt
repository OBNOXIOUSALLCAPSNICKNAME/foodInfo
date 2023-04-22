package com.example.foodinfo.features.home.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodinfo.domain.State
import com.example.foodinfo.features.home.interactor.CategoryModelInteractor
import com.example.foodinfo.features.home.model.CategoryVHModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    private val categoryModelInteractor: CategoryModelInteractor
) : ViewModel() {

    val categories: SharedFlow<State<List<CategoryVHModel>>> by lazy {
        categoryModelInteractor.getCategories().shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }
}