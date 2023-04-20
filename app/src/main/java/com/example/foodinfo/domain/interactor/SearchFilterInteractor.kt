package com.example.foodinfo.domain.interactor

import android.content.Context
import androidx.paging.PagingConfig
import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.State.Utils.transformData
import com.example.foodinfo.domain.model.CategoryOfSearchFilter
import com.example.foodinfo.domain.model.NutrientOfSearchFilter
import com.example.foodinfo.domain.model.PagingHelper
import com.example.foodinfo.domain.model.SearchFilter
import com.example.foodinfo.domain.repository.SearchFilterRepository
import com.example.foodinfo.utils.extensions.hasInternet
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SearchFilterInteractor @Inject constructor(
    private val searchFilterRepository: SearchFilterRepository,
    private val recipeMetadataInteractor: RecipeMetadataInteractor,
    private val context: Context
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

    fun getPagingHelper(pagingConfig: PagingConfig, inputText: String): Flow<State<PagingHelper>> {
        return getResolved(
            extraData = recipeMetadataInteractor.getRecipeMetadata(),
            outputDataProvider = { metadata ->
                searchFilterRepository.getFilterPreset(metadata).transformData { filterPreset ->
                    PagingHelper(
                        recipeMetadata = metadata,
                        filterPreset = filterPreset,
                        pagingConfig = pagingConfig,
                        inputText = inputText,
                        isOnline = context.hasInternet()
                    )
                }
            }
        )
    }

    fun getPagingHelper(pagingConfig: PagingConfig, labelID: Int): Flow<State<PagingHelper>> {
        return getResolved(
            extraData = recipeMetadataInteractor.getRecipeMetadata(),
            outputDataProvider = { metadata ->
                searchFilterRepository.getFilterPreset(metadata, labelID).transformData { filterPreset ->
                    PagingHelper(
                        recipeMetadata = metadata,
                        filterPreset = filterPreset,
                        pagingConfig = pagingConfig,
                        inputText = "",
                        isOnline = context.hasInternet()
                    )
                }
            }
        )
    }
}