package com.example.foodinfo.domain.interactor

import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.model.CategoryOfSearchFilter
import com.example.foodinfo.domain.model.NutrientOfSearchFilter
import com.example.foodinfo.domain.model.SearchFilter
import com.example.foodinfo.domain.model.SearchFilterPreset
import com.example.foodinfo.domain.repository.SearchFilterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SearchFilterInteractor @Inject constructor(
    private val searchFilterRepository: SearchFilterRepository,
    private val recipeAttrsInteractor: RecipeAttrsInteractor
) {

    fun getCategory(categoryID: Int): Flow<State<CategoryOfSearchFilter>> {
        return getResolved(
            extraData = recipeAttrsInteractor.getLabels(),
            outputDataProvider = { attrs ->
                searchFilterRepository.getCategory(categoryID, attrs)
            }
        )
    }

    fun getNutrients(): Flow<State<List<NutrientOfSearchFilter>>> {
        return getResolved(
            extraData = recipeAttrsInteractor.getNutrients(),
            outputDataProvider = { attrs ->
                searchFilterRepository.getNutrients(attrs)
            }
        )
    }


    fun getFilter(): Flow<State<SearchFilter>> {
        return getResolved(
            extraData = recipeAttrsInteractor.getRecipeAttrs(),
            outputDataProvider = { attrs ->
                searchFilterRepository.getFilter(attrs)
            }
        )
    }

    fun getFilterPreset(): Flow<State<SearchFilterPreset>> {
        return getResolved(
            extraData = recipeAttrsInteractor.getRecipeAttrs(),
            outputDataProvider = { attrs ->
                searchFilterRepository.getFilterPreset(attrs)
            }
        )
    }

    fun getFilterPreset(labelID: Int): Flow<State<SearchFilterPreset>> {
        return getResolved(
            extraData = recipeAttrsInteractor.getRecipeAttrs(),
            outputDataProvider = { attrs ->
                searchFilterRepository.getFilterPreset(attrs, labelID)
            }
        )
    }
}