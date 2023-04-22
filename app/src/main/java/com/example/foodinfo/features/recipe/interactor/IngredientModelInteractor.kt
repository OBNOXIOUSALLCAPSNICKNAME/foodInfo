package com.example.foodinfo.features.recipe.interactor

import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.State.Utils.transformData
import com.example.foodinfo.domain.interactor.RecipeInteractor
import com.example.foodinfo.domain.model.IngredientOfRecipe
import com.example.foodinfo.features.recipe.mapper.toVHModel
import com.example.foodinfo.features.recipe.model.IngredientVHModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class IngredientModelInteractor @Inject constructor(
    private val recipeInteractor: RecipeInteractor,
) {

    fun getByIdIngredients(ID: String): Flow<State<List<IngredientVHModel>>> {
        return recipeInteractor.getByIdIngredients(ID).transformData { ingredients ->
            ingredients.map(IngredientOfRecipe::toVHModel)
        }
    }
}