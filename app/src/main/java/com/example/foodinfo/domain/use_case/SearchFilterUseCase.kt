package com.example.foodinfo.domain.use_case

import android.content.Context
import com.example.foodinfo.domain.model.CategoryOfSearchFilterEditModel
import com.example.foodinfo.domain.model.NutrientOfSearchFilterEditModel
import com.example.foodinfo.domain.model.SearchFilterEditModel
import com.example.foodinfo.domain.repository.RecipeAttrRepository
import com.example.foodinfo.domain.repository.SearchFilterRepository
import com.example.foodinfo.domain.state.State
import com.example.foodinfo.domain.state.State.Utils.transformData
import com.example.foodinfo.domain.state.getResolved
import com.example.foodinfo.utils.extensions.hasInternet
import com.example.foodinfo.utils.paging.PageFetchHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SearchFilterUseCase @Inject constructor(
    private val recipeAttrRepository: RecipeAttrRepository,
    private val searchFilterRepository: SearchFilterRepository,
    private val context: Context
) {

    fun getHelper(): Flow<State<PageFetchHelper>> {
        return getResolved(
            extraData = recipeAttrRepository.getRecipeAttrsDBLatest(),
            outputDataProvider = { attrs ->
                searchFilterRepository.getFilterPreset(attrs).transformData { preset ->
                    PageFetchHelper(
                        recipeAttrs = attrs,
                        filterPreset = preset,
                        isOnline = context.hasInternet()
                    )
                }
            }
        )
    }

    fun getHelper(labelID: Int): Flow<State<PageFetchHelper>> {
        return getResolved(
            extraData = recipeAttrRepository.getRecipeAttrsDBLatest(),
            outputDataProvider = { attrs ->
                searchFilterRepository.getFilterPreset(attrs, labelID).transformData { preset ->
                    PageFetchHelper(
                        recipeAttrs = attrs,
                        filterPreset = preset,
                        isOnline = context.hasInternet()
                    )
                }
            }
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