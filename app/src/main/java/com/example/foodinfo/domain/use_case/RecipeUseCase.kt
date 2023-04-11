package com.example.foodinfo.domain.use_case

import com.example.foodinfo.domain.repository.RecipeAttrRepository
import com.example.foodinfo.domain.repository.RecipeRepository
import com.example.foodinfo.domain.model.NutrientOfRecipeModel
import com.example.foodinfo.domain.model.RecipeExtendedModel
import com.example.foodinfo.domain.state.State
import com.example.foodinfo.domain.state.getResolved
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RecipeUseCase @Inject constructor(
    private val recipeAttrRepository: RecipeAttrRepository,
    private val recipeRepository: RecipeRepository
) {

    fun getByIdExtended(recipeID: String): Flow<State<RecipeExtendedModel>> {
        return getResolved(
            extraData = recipeAttrRepository.getRecipeAttrsDBLatest(),
            outputDataProvider = { recipeRepository.getByIdExtended(recipeID, it) }
        )
    }

    fun getByIdNutrients(recipeID: String): Flow<State<List<NutrientOfRecipeModel>>> {
        return getResolved(
            extraData = recipeAttrRepository.getNutrientsDBLatest(),
            outputDataProvider = { recipeRepository.getByIdNutrients(recipeID, it) }
        )
    }
}