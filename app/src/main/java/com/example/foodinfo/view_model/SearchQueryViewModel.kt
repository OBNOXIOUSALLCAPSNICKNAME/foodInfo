package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.foodinfo.repository.RecipeRepository
import com.example.foodinfo.repository.model.RecipeShortModel
import com.example.foodinfo.repository.model.SearchFilterPresetModel
import com.example.foodinfo.repository.use_case.SearchFilterUseCase
import com.example.foodinfo.utils.State
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


class SearchQueryViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val searchFilterUseCase: SearchFilterUseCase
) : ViewModel() {

    lateinit var inputText: String
    lateinit var query: SearchFilterPresetModel

    val filterQuery: SharedFlow<State<SearchFilterPresetModel>> by lazy {
        searchFilterUseCase.getSearchQuery()
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1)
    }

    val recipes: SharedFlow<PagingData<RecipeShortModel>> by lazy {
        recipeRepository.getByFilter(query, inputText)
            .cachedIn(viewModelScope)
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1)
    }


    fun invertFavoriteStatus(recipeId: String) {
        recipeRepository.invertFavoriteStatus(recipeId)
    }
}