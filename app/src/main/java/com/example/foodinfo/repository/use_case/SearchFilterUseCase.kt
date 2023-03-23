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

    fun getPreset(): Flow<State<SearchFilterPresetModel>> {
        return getResolved(
            extraData = recipeAttrRepository.getRecipeAttrsDBLatest(),
            outputDataProvider = { searchFilterRepository.getFilterPreset(it) }
        )
    }

    fun getPresetByLabel(labelID: Int): Flow<State<SearchFilterPresetModel>> {
        return getResolved(
            extraData = recipeAttrRepository.getRecipeAttrsDBLatest(),
            outputDataProvider = { searchFilterRepository.getFilterPresetByLabel(labelID, it) }
        )
    }

    fun getFilterEdit(): Flow<State<SearchFilterEditModel>> {
        return getResolved(
            extraData = recipeAttrRepository.getRecipeAttrsDBLatest(),
            outputDataProvider = { searchFilterRepository.getFilterEdit(it) }
        )
    }

    fun getCategoryEdit(categoryID: Int): Flow<State<CategoryOfSearchFilterEditModel>> {
        return getResolved(
            extraData = recipeAttrRepository.getLabelsDBLatest(),
            outputDataProvider = { searchFilterRepository.getCategoryEdit(categoryID, it) }
        )
    }

    fun getNutrientsEdit(): Flow<State<List<NutrientOfSearchFilterEditModel>>> {
        return getResolved(
            extraData = recipeAttrRepository.getNutrientsDBLatest(),
            outputDataProvider = { searchFilterRepository.getNutrientsEdit(it) }
        )
    }
}