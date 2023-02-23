package com.example.foodinfo.remote.api

import com.example.foodinfo.remote.dto.*


abstract class RecipeAPI {

    abstract fun getRecipes(ID: String): List<RecipeNetwork>

    abstract fun getRecipeExtended(ID: String): RecipeExtendedNetwork?

    abstract fun getLabels(ID: String): List<LabelOfRecipeNetwork>

    abstract fun getNutrients(ID: String): List<NutrientOfRecipeNetwork>

    abstract fun getIngredients(ID: String): List<IngredientOfRecipeNetwork>
}