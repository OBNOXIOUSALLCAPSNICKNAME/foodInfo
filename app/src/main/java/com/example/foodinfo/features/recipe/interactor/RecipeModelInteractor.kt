package com.example.foodinfo.features.recipe.interactor

import com.example.foodinfo.domain.State
import com.example.foodinfo.domain.State.Utils.transformData
import com.example.foodinfo.domain.interactor.RecipeInteractor
import com.example.foodinfo.domain.model.RecipeExtended
import com.example.foodinfo.features.recipe.mapper.toModel
import com.example.foodinfo.features.recipe.model.RecipeModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RecipeModelInteractor @Inject constructor(
    private val recipeInteractor: RecipeInteractor
) {

    fun getByIdExtended(ID: String): Flow<State<RecipeModel>> {
        return recipeInteractor.getByIdExtended(ID).transformData(RecipeExtended::toModel)
    }
}