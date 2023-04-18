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
    private val recipeAttrsInteractor: RecipeAttrsInteractor,
    private val recipeRepository: RecipeRepository,
    private val prefUtils: PrefUtils
) {

    fun getByIdExtended(recipeID: String): Flow<State<RecipeExtended>> {
        return getResolved(
            extraData = recipeAttrsInteractor.getRecipeAttrs(),
            outputDataProvider = { attrs ->
                recipeRepository.getByIdExtended(
                    apiCredentialsRepository.getEdamam(prefUtils.edamamCredentials),
                    recipeID,
                    attrs
                )
            }
        )
    }

    fun getByIdNutrients(recipeID: String): Flow<State<List<NutrientOfRecipe>>> {
        return getResolved(
            extraData = recipeAttrsInteractor.getNutrients(),
            outputDataProvider = { attrs ->
                recipeRepository.getByIdNutrients(
                    apiCredentialsRepository.getEdamam(prefUtils.edamamCredentials),
                    recipeID,
                    attrs
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
        pagingConfig: PagingConfig,
        filterPreset: SearchFilterPreset,
        recipeAttrs: RecipeAttrs,
        inputText: String = "",
        isOnline: Boolean
    ): Flow<PagingData<Recipe>> = flow {
        emitAll(
            recipeRepository.getByFilter(
                apiCredentialsRepository.getEdamam(prefUtils.edamamCredentials),
                pagingConfig,
                filterPreset,
                recipeAttrs,
                inputText,
                isOnline
            )
        )
    }
}
