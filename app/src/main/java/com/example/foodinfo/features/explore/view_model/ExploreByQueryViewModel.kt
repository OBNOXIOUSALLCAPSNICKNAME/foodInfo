package com.example.foodinfo.features.explore.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.interactor.SearchFilterInteractor
import com.example.foodinfo.domain.model.PagingHelper
import com.example.foodinfo.domain.repository.RecipeRepository
import com.example.foodinfo.features.explore.interactor.RecipeModelInteractor
import com.example.foodinfo.utils.CoroutineLauncher
import com.example.foodinfo.utils.LaunchStrategy
import com.example.foodinfo.utils.paging.AppPagingConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class ExploreByQueryViewModel @Inject constructor(
    private val recipeModelInteractor: RecipeModelInteractor,
    private val searchFilterInteractor: SearchFilterInteractor,
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val invertCoroutine = CoroutineLauncher(
        viewModelScope, Dispatchers.IO, LaunchStrategy.IGNORE
    )

    var inputText: String? = null

    private val _pagingHelper = MutableSharedFlow<PagingHelper>(extraBufferCapacity = 1)

    val pagingHelper: SharedFlow<State<PagingHelper>> by lazy {
        searchFilterInteractor.getPagingHelper(AppPagingConfig.RECIPE_EXPLORE_PAGER, inputText!!)
            .shareIn(viewModelScope, SharingStarted.Lazily, 0)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val recipes = _pagingHelper
        .flatMapLatest(recipeModelInteractor::getByFilter)
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty())


    fun setHelper(helper: PagingHelper) {
        _pagingHelper.tryEmit(helper)
    }

    fun invertFavoriteStatus(ID: String) {
        invertCoroutine.launch {
            recipeRepository.invertFavoriteStatus(ID)
        }
    }
}