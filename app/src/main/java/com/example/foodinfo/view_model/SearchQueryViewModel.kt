package com.example.foodinfo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.foodinfo.domain.repository.RecipeRepository
import com.example.foodinfo.domain.state.State
import com.example.foodinfo.domain.use_case.SearchFilterUseCase
import com.example.foodinfo.utils.CoroutineLauncher
import com.example.foodinfo.utils.LaunchStrategy
import com.example.foodinfo.utils.paging.AppPagingConfig
import com.example.foodinfo.utils.paging.PageFetchHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class SearchQueryViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val searchFilterUseCase: SearchFilterUseCase
) : ViewModel() {

    private val invertCoroutine = CoroutineLauncher(
        viewModelScope, Dispatchers.IO, LaunchStrategy.IGNORE
    )

    var inputText: String? = null

    private val _pagingHelper = MutableSharedFlow<PageFetchHelper>(extraBufferCapacity = 1)

    val pagingHelper: SharedFlow<State<PageFetchHelper>> by lazy {
        searchFilterUseCase.getHelper(inputText!!)
            .shareIn(viewModelScope, SharingStarted.Lazily, 0)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val recipes = _pagingHelper
        .flatMapLatest { pageFetchHelper ->
            recipeRepository.getByFilter(
                pagingHelper = pageFetchHelper,
                pagingConfig = AppPagingConfig.RECIPE_EXPLORE_PAGER
            )
        }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty())


    fun setHelper(helper: PageFetchHelper) {
        _pagingHelper.tryEmit(helper)
    }

    fun invertFavoriteStatus(recipeId: String) {
        invertCoroutine.launch {
            recipeRepository.invertFavoriteStatus(recipeId)
        }
    }
}