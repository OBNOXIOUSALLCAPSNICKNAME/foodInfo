package com.example.foodinfo.repository.use_case

import com.example.foodinfo.repository.RecipeAttrRepository
import com.example.foodinfo.repository.RecipeRepository
import com.example.foodinfo.repository.model.NutrientOfRecipeModel
import com.example.foodinfo.repository.model.RecipeExtendedModel
import com.example.foodinfo.repository.state_handling.State
import com.example.foodinfo.repository.state_handling.getResolved
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