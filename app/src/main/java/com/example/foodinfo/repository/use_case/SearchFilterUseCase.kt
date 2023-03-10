package com.example.foodinfo.repository.use_case

import com.example.foodinfo.repository.RecipeAttrRepository
import com.example.foodinfo.repository.SearchFilterRepository
import com.example.foodinfo.repository.model.CategoryOfSearchFilterEditModel
import com.example.foodinfo.repository.model.NutrientOfSearchFilterEditModel
import com.example.foodinfo.repository.model.SearchFilterEditModel
import com.example.foodinfo.repository.model.SearchFilterPresetModel
import com.example.foodinfo.utils.State
import com.example.foodinfo.utils.getResolved
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SearchFilterUseCase @Inject constructor(
    private val recipeAttrRepository: RecipeAttrRepository,
    private val searchFilterRepository: SearchFilterRepository
) {

    fun getSearchQuery(): Flow<State<SearchFilterPresetModel>> {
        return getResolved(
            extraDataFlow = recipeAttrRepository.getRecipeAttrsDBLatest(),
            dataFlowProvider = { attrs ->
                searchFilterRepository.getFilterPreset(attrs)
            }
        )
    }

    fun getSearchQueryByLabel(labelID: Int): Flow<State<SearchFilterPresetModel>> {
        return getResolved(
            extraDataFlow = recipeAttrRepository.getRecipeAttrsDBLatest(),
            dataFlowProvider = { attrs ->
                searchFilterRepository.getFilterPresetByLabel(labelID, attrs)
            }
        )
    }

    fun getFilterEdit(): Flow<State<SearchFilterEditModel>> {
        return getResolved(
            extraDataFlow = recipeAttrRepository.getRecipeAttrsDBLatest(),
            dataFlowProvider = { searchFilterRepository.getFilterEdit(it) }
        )
    }

    fun getCategoryEdit(categoryID: Int): Flow<State<CategoryOfSearchFilterEditModel>> {
        return getResolved(
            extraDataFlow = recipeAttrRepository.getLabelsDBLatest(),
            dataFlowProvider = { searchFilterRepository.getCategoryEdit(categoryID, it) }
        )
    }

    fun getNutrientsEdit(): Flow<State<List<NutrientOfSearchFilterEditModel>>> {
        return getResolved(
            extraDataFlow = recipeAttrRepository.getNutrientsDBLatest(),
            dataFlowProvider = { searchFilterRepository.getNutrientsEdit(it) }
        )
    }
}