package com.example.foodinfo.remote.api

import com.example.foodinfo.remote.dto.*


abstract class RecipeAPI {

    abstract fun getRecipe(ID: String): RecipeNetwork?

    abstract fun getRecipeExtended(ID: String): RecipeExtendedNetwork?

    abstract fun getLabels(ID: String): LabelOfRecipeNetwork?

    abstract fun getNutrients(ID: String): NutrientOfRecipeNetwork?

    abstract fun getIngredients(ID: String): IngredientOfRecipeNetwork?
}