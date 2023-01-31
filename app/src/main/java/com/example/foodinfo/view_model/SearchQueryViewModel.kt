package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.foodinfo.repository.RecipeRepository
import com.example.foodinfo.repository.SearchFilterRepository
import com.example.foodinfo.repository.model.RecipeShortModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


class SearchQueryViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val searchFilterRepository: SearchFilterRepository
) : ViewModel() {

    lateinit var inputText: String

    val recipes: SharedFlow<PagingData<RecipeShortModel>> by lazy {
        recipeRepository.getByFilter(searchFilterRepository.getQueryByFilter(inputText = inputText))
            .cachedIn(viewModelScope)
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1)
    }


    fun invertFavoriteStatus(recipeId: String) {
        recipeRepository.invertFavoriteStatus(recipeId)
    }
}