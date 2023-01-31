package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.foodinfo.repository.RecipeAttrRepository
import com.example.foodinfo.repository.RecipeRepository
import com.example.foodinfo.repository.SearchFilterRepository
import com.example.foodinfo.repository.model.LabelHintModel
import com.example.foodinfo.repository.model.RecipeShortModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


class SearchLabelViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val recipeAttrRepository: RecipeAttrRepository,
    private val searchFilterRepository: SearchFilterRepository
) : ViewModel() {

    var labelID: Int = -1

    val recipes: SharedFlow<PagingData<RecipeShortModel>> by lazy {
        recipeRepository.getByFilter(searchFilterRepository.getQueryByLabel(labelID))
            .cachedIn(viewModelScope)
            .shareIn(viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1)
    }

    fun invertFavoriteStatus(recipeId: String) {
        recipeRepository.invertFavoriteStatus(recipeId)
    }

    fun getLabel(labelID: Int): LabelHintModel {
        return recipeAttrRepository.getLabelHint(labelID)
    }
}