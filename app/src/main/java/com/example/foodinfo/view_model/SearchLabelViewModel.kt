package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.foodinfo.repository.RecipeAttrRepository
import com.example.foodinfo.repository.RecipeRepository
import com.example.foodinfo.repository.model.LabelHintModel
import com.example.foodinfo.repository.model.SearchFilterPresetModel
import com.example.foodinfo.repository.use_case.SearchFilterUseCase
import com.example.foodinfo.utils.AppPagingConfig
import com.example.foodinfo.utils.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class SearchLabelViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val recipeAttrRepository: RecipeAttrRepository,
    private val searchFilterUseCase: SearchFilterUseCase
) : ViewModel() {

    var labelID: Int? = null

    private val _filterPreset = MutableSharedFlow<SearchFilterPresetModel>(extraBufferCapacity = 1)

    val filterPreset: SharedFlow<State<SearchFilterPresetModel>> by lazy {
        searchFilterUseCase.getPresetByLabel(labelID!!)
            .shareIn(viewModelScope, SharingStarted.Lazily, 0)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val recipes = _filterPreset
        .flatMapLatest { filterPreset ->
            recipeRepository.getByFilter(
                searchFilterPreset = filterPreset,
                pagingConfig = AppPagingConfig.RECIPE_FAVORITE_PAGER
            )
        }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty())


    fun setPreset(preset: SearchFilterPresetModel) {
        _filterPreset.tryEmit(preset)
    }

    fun invertFavoriteStatus(recipeId: String) {
        recipeRepository.invertFavoriteStatus(recipeId)
    }

    fun getLabel(labelID: Int): LabelHintModel {
        return recipeAttrRepository.getLabelHint(labelID)
    }
}