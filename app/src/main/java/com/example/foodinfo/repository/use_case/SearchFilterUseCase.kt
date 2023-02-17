package com.example.foodinfo.repository.use_case

import com.example.foodinfo.local.dto.SearchFilterDB
import com.example.foodinfo.repository.RecipeAttrRepository
import com.example.foodinfo.repository.SearchFilterRepository
import com.example.foodinfo.repository.model.CategoryOfSearchFilterEditModel
import com.example.foodinfo.repository.model.NutrientOfSearchFilterEditModel
import com.example.foodinfo.repository.model.SearchFilterEditModel
import com.example.foodinfo.utils.State
import com.example.foodinfo.utils.getResolved
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SearchFilterUseCase @Inject constructor(
    private val recipeAttrRepository: RecipeAttrRepository,
    private val searchFilterRepository: SearchFilterRepository
) {

    fun getQueryByFilter(
        filterName: String = SearchFilterDB.DEFAULT_NAME,
        inputText: String
    ): Flow<State<String>> {
        return getResolved(
            extraDataFlow = recipeAttrRepository.getRecipeAttrsDBLatest(),
            dataFlowProvider = { searchFilterRepository.getQueryByFilter(filterName, inputText, it) }
        )
    }

    fun getQueryByLabel(labelID: Int): Flow<State<String>> {
        return getResolved(
            extraDataFlow = recipeAttrRepository.getRecipeAttrsDBLatest(),
            dataFlowProvider = { searchFilterRepository.getQueryByLabel(labelID, it) }
        )
    }

    fun getCategoryEdit(
        filterName: String = SearchFilterDB.DEFAULT_NAME,
        categoryID: Int
    ): Flow<State<CategoryOfSearchFilterEditModel>> {
        return getResolved(
            extraDataFlow = recipeAttrRepository.getLabelsDBLatest(),
            dataFlowProvider = { searchFilterRepository.getCategoryEdit(filterName, categoryID, it) }
        )
    }

    fun getNutrientsEdit(
        filterName: String = SearchFilterDB.DEFAULT_NAME
    ): Flow<State<List<NutrientOfSearchFilterEditModel>>> {
        return getResolved(
            extraDataFlow = recipeAttrRepository.getNutrientsDBLatest(),
            dataFlowProvider = { searchFilterRepository.getNutrientsEdit(filterName, it) }
        )
    }

    fun getFilterEdit(
        filterName: String = SearchFilterDB.DEFAULT_NAME
    ): Flow<State<SearchFilterEditModel>> {
        return getResolved(
            extraDataFlow = recipeAttrRepository.getRecipeAttrsDBLatest(),
            dataFlowProvider = { searchFilterRepository.getFilterEdit(filterName, it) }
        )
    }
}