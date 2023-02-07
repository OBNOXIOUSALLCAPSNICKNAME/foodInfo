package com.example.foodinfo.repository.use_case

import com.example.foodinfo.repository.RecipeAttrRepository
import com.example.foodinfo.repository.RecipeRepository
import com.example.foodinfo.repository.model.NutrientOfRecipeModel
import com.example.foodinfo.repository.model.RecipeExtendedModel
import com.example.foodinfo.utils.State
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RecipeUseCase @Inject constructor(
    private val recipeAttrRepository: RecipeAttrRepository,
    private val recipeRepository: RecipeRepository
) : AttrDependencyResolver {

    fun getByIdExtended(recipeID: String): Flow<State<RecipeExtendedModel>> {
        return getResolved(
            attrFlow = recipeAttrRepository.getRecipeAttrsDB(),
            dataFlowProvider = { attrs -> recipeRepository.getByIdExtended(recipeID, attrs) }
        )
    }

    fun getByIdNutrients(recipeID: String): Flow<State<List<NutrientOfRecipeModel>>> {
        return getResolved(
            attrFlow = recipeAttrRepository.getNutrientsDB(),
            dataFlowProvider = { attrs -> recipeRepository.getByIdNutrients(recipeID, attrs) }
        )
    }
}