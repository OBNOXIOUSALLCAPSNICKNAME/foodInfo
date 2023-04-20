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
    private val recipeMetadataInteractor: RecipeMetadataInteractor
) {

    fun getCategory(categoryID: Int): Flow<State<CategoryOfSearchFilter>> {
        return getResolved(
            extraData = recipeMetadataInteractor.getLabels(),
            outputDataProvider = { metadata ->
                searchFilterRepository.getCategory(categoryID, metadata)
            }
        )
    }

    fun getNutrients(): Flow<State<List<NutrientOfSearchFilter>>> {
        return getResolved(
            extraData = recipeMetadataInteractor.getNutrients(),
            outputDataProvider = { metadata ->
                searchFilterRepository.getNutrients(metadata)
            }
        )
    }


    fun getFilter(): Flow<State<SearchFilter>> {
        return getResolved(
            extraData = recipeMetadataInteractor.getRecipeMetadata(),
            outputDataProvider = { metadata ->
                searchFilterRepository.getFilter(metadata)
            }
        )
    }

    fun getFilterPreset(): Flow<State<SearchFilterPreset>> {
        return getResolved(
            extraData = recipeMetadataInteractor.getRecipeMetadata(),
            outputDataProvider = { metadata ->
                searchFilterRepository.getFilterPreset(metadata)
            }
        )
    }

    fun getFilterPreset(labelID: Int): Flow<State<SearchFilterPreset>> {
        return getResolved(
            extraData = recipeMetadataInteractor.getRecipeMetadata(),
            outputDataProvider = { metadata ->
                searchFilterRepository.getFilterPreset(metadata, labelID)
            }
        )
    }
}