package com.example.foodinfo.features.recipe.interactor

import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.State.Utils.transformData
import com.example.foodinfo.domain.interactor.RecipeInteractor
import com.example.foodinfo.domain.model.NutrientOfRecipe
import com.example.foodinfo.features.recipe.mapper.toVHModel
import com.example.foodinfo.features.recipe.model.NutrientVHModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class NutrientModelInteractor @Inject constructor(
    private val recipeInteractor: RecipeInteractor
) {

    fun getByIdNutrients(ID: String): Flow<State<List<NutrientVHModel>>> {
        return recipeInteractor.getByIdNutrients(ID).transformData { nutrients ->
            nutrients.map(NutrientOfRecipe::toVHModel)
        }
    }
}