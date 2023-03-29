package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.foodinfo.repository.RecipeAttrRepository
import com.example.foodinfo.repository.RecipeRepository
import com.example.foodinfo.repository.model.LabelHintModel
import com.example.foodinfo.repository.use_case.SearchFilterUseCase
import com.example.foodinfo.utils.paging.AppPagingConfig
import com.example.foodinfo.utils.paging.PageFetchHelper
import com.example.foodinfo.repository.state_handling.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class SearchLabelViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val recipeAttrRepository: RecipeAttrRepository,
    private val searchFilterUseCase: SearchFilterUseCase
) : ViewModel() {

    var labelID: Int? = null

    private val _pagingHelper = MutableSharedFlow<PageFetchHelper>(extraBufferCapacity = 1)

    val pagingHelper: SharedFlow<State<PageFetchHelper>> by lazy {
        searchFilterUseCase.getHelperByLabel(labelID!!)
            .shareIn(viewModelScope, SharingStarted.Lazily, 0)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val recipes = _pagingHelper
        .flatMapLatest { pagingHelper ->
            recipeRepository.getByFilter(
                pagingHelper = pagingHelper,
                pagingConfig = AppPagingConfig.RECIPE_EXPLORE_PAGER
            )
        }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty())


    fun setPreset(helper: PageFetchHelper) {
        _pagingHelper.tryEmit(helper)
    }

    fun invertFavoriteStatus(recipeId: String) {
        recipeRepository.invertFavoriteStatus(recipeId)
    }

    fun getLabel(labelID: Int): LabelHintModel {
        return recipeAttrRepository.getLabelHint(labelID)
    }
}