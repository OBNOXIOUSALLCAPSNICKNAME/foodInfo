package com.example.foodinfo.domain.interactor

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.State.Utils.suspendFlowProvider
import com.example.foodinfo.domain.model.*
import com.example.foodinfo.domain.repository.APICredentialsRepository
import com.example.foodinfo.domain.repository.RecipeRepository
import com.example.foodinfo.utils.PrefUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class RecipeInteractor @Inject constructor(
    private val apiCredentialsRepository: APICredentialsRepository,
    private val recipeMetadataInteractor: RecipeMetadataInteractor,
    private val recipeRepository: RecipeRepository,
    private val prefUtils: PrefUtils
) {

    fun getByIdExtended(recipeID: String): Flow<State<RecipeExtended>> {
        return getResolved(
            extraData = recipeMetadataInteractor.getRecipeMetadata(),
            outputDataProvider = { metadata ->
                recipeRepository.getByIdExtended(
                    apiCredentialsRepository.getEdamam(prefUtils.edamamCredentials),
                    metadata,
                    recipeID
                )
            }
        )
    }

    fun getByIdNutrients(recipeID: String): Flow<State<List<NutrientOfRecipe>>> {
        return getResolved(
            extraData = recipeMetadataInteractor.getNutrients(),
            outputDataProvider = { metadata ->
                recipeRepository.getByIdNutrients(
                    apiCredentialsRepository.getEdamam(prefUtils.edamamCredentials),
                    metadata,
                    recipeID
                )
            }
        )
    }

    fun getByIdIngredients(recipeID: String): Flow<State<List<IngredientOfRecipe>>> {
        return suspendFlowProvider {
            recipeRepository.getByIdIngredients(
                apiCredentialsRepository.getEdamam(prefUtils.edamamCredentials),
                recipeID,
            )
        }
    }

    fun getByFilter(
        recipeMetadata: RecipeMetadata,
        filterPreset: SearchFilterPreset,
        pagingConfig: PagingConfig,
        inputText: String = "",
        isOnline: Boolean
    ): Flow<PagingData<Recipe>> = flow {
        emitAll(
            recipeRepository.getByFilter(
                apiCredentialsRepository.getEdamam(prefUtils.edamamCredentials),
                recipeMetadata,
                filterPreset,
                pagingConfig,
                inputText,
                isOnline
            )
        )
    }
}
