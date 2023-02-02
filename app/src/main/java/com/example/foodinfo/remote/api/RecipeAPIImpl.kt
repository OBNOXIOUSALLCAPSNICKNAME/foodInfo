package com.example.foodinfo.remote.api

import com.example.foodinfo.remote.dto.*


class RecipeAPIImpl : RecipeAPI() {

    override fun getRecipe(ID: String): RecipeNetwork? {
        return RecipeNetwork()
    }

    override fun getRecipeExtended(ID: String): RecipeExtendedNetwork? {
        return RecipeExtendedNetwork()
    }

    override fun getLabels(ID: String): LabelOfRecipeNetwork? {
        return LabelOfRecipeNetwork()
    }

    override fun getNutrients(ID: String): NutrientOfRecipeNetwork? {
        return NutrientOfRecipeNetwork()
    }

    override fun getIngredients(ID: String): IngredientOfRecipeNetwork? {
        return IngredientOfRecipeNetwork()
    }
}