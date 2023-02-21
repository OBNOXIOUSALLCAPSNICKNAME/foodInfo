package com.example.foodinfo.repository.use_case

import com.example.foodinfo.local.dto.SearchFilterDB
import com.example.foodinfo.repository.RecipeAttrRepository
import com.example.foodinfo.repository.SearchFilterRepository
import com.example.foodinfo.repository.model.CategoryOfSearchFilterEditModel
import com.example.foodinfo.repository.model.NutrientOfSearchFilterEditModel
import com.example.foodinfo.repository.model.SearchFilterEditModel
import com.example.foodinfo.utils.APICredentials
import com.example.foodinfo.utils.SearchFilterQuery
import com.example.foodinfo.utils.State
import com.example.foodinfo.utils.getResolved
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject


class SearchFilterUseCase @Inject constructor(
    private val recipeAttrRepository: RecipeAttrRepository,
    private val searchFilterRepository: SearchFilterRepository
) {

    fun getSearchQuery(
        filterName: String = SearchFilterDB.DEFAULT_NAME,
        inputText: String
    ): Flow<State<SearchFilterQuery>> {
        return getResolved(
            extraDataFlow = recipeAttrRepository.getRecipeAttrsDBLatest(),
            dataFlowProvider = { attrs ->
                searchFilterRepository.getFilterPreset(filterName, attrs)
            }
        ).transform { state ->
            val query = if (state.data != null) {
                SearchFilterQuery(
                    searchFilterPreset = state.data,
                    apiCredentials = APICredentials(),
                    isOffline = true,
                    inputText = inputText
                )
            } else null
            when (state) {
                is State.Success -> {
                    emit(State.Success(query!!))
                }
                is State.Error   -> {
                    emit(State.Error(state.message!!, state.error!!))
                }
                is State.Loading -> {
                    emit(State.Loading(query))
                }
            }
        }
    }

    fun getSearchQueryByLabel(
        labelID: Int
    ): Flow<State<SearchFilterQuery>> {
        return getResolved(
            extraDataFlow = recipeAttrRepository.getRecipeAttrsDBLatest(),
            dataFlowProvider = { attrs ->
                searchFilterRepository.getFilterPresetByLabel(labelID, attrs)
            }
        ).transform { state ->
            val query = if (state.data != null) {
                SearchFilterQuery(
                    searchFilterPreset = state.data,
                    apiCredentials = APICredentials(),
                    isOffline = true
                )
            } else null
            when (state) {
                is State.Success -> {
                    emit(State.Success(query!!))
                }
                is State.Error   -> {
                    emit(State.Error(state.message!!, state.error!!))
                }
                is State.Loading -> {
                    emit(State.Loading(query))
                }
            }
        }
    }

    fun getFilterEdit(
        filterName: String = SearchFilterDB.DEFAULT_NAME
    ): Flow<State<SearchFilterEditModel>> {
        return getResolved(
            extraDataFlow = recipeAttrRepository.getRecipeAttrsDBLatest(),
            dataFlowProvider = { searchFilterRepository.getFilterEdit(filterName, it) }
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
}