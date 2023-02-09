package com.example.foodinfo.repository.use_case

import com.example.foodinfo.local.dto.SearchFilterDB
import com.example.foodinfo.repository.RecipeAttrRepository
import com.example.foodinfo.repository.SearchFilterRepository
import com.example.foodinfo.repository.model.CategoryOfSearchFilterEditModel
import com.example.foodinfo.repository.model.NutrientOfSearchFilterEditModel
import com.example.foodinfo.repository.model.SearchFilterEditModel
import com.example.foodinfo.utils.State
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SearchFilterUseCase @Inject constructor(
    private val recipeAttrRepository: RecipeAttrRepository,
    private val searchFilterRepository: SearchFilterRepository
) : AttrDependencyResolver {

    fun getQueryByFilter(
        filterName: String = SearchFilterDB.DEFAULT_NAME,
        inputText: String
    ): Flow<State<String>> {
        return getResolved(
            attrFlow = recipeAttrRepository.getRecipeAttrsDB(),
            dataFlowProvider = { searchFilterRepository.getQueryByFilter(filterName, inputText) }
        )
    }

    fun getQueryByLabel(labelID: Int): Flow<State<String>> {
        return getResolved(
            attrFlow = recipeAttrRepository.getLabelsExtendedDB(),
            dataFlowProvider = { searchFilterRepository.getQueryByLabel(labelID) }
        )
    }

    fun getCategoryEdit(
        filterName: String = SearchFilterDB.DEFAULT_NAME,
        categoryID: Int
    ): Flow<State<CategoryOfSearchFilterEditModel>> {
        return getResolved(
            attrFlow = recipeAttrRepository.getLabelsDB(),
            dataFlowProvider = { searchFilterRepository.getCategoryEdit(filterName, categoryID, it) }
        )
    }

    fun getNutrientsEdit(
        filterName: String = SearchFilterDB.DEFAULT_NAME
    ): Flow<State<List<NutrientOfSearchFilterEditModel>>> {
        return getResolved(
            attrFlow = recipeAttrRepository.getNutrientsDB(),
            dataFlowProvider = { searchFilterRepository.getNutrientsEdit(filterName, it) }
        )
    }

    fun getFilterEdit(
        filterName: String = SearchFilterDB.DEFAULT_NAME
    ): Flow<State<SearchFilterEditModel>> {
        return getResolved(
            attrFlow = recipeAttrRepository.getRecipeAttrsDB(),
            dataFlowProvider = { searchFilterRepository.getFilterEdit(filterName, it) }
        )
    }
}