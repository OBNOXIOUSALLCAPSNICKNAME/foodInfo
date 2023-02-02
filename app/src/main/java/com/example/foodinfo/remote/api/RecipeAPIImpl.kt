package com.example.foodinfo.remote.api

import com.example.foodinfo.remote.dto.*


class RecipeAPIImpl : RecipeAPI() {

    override fun getRecipes(ID: String): List<RecipeNetwork> {
        return listOf()
    }

    override fun getRecipeExtended(ID: String): RecipeExtendedNetwork? {
        return RecipeExtendedNetwork()
    }

    override fun getLabels(ID: String): List<LabelOfRecipeNetwork> {
        return listOf()
    }

    override fun getNutrients(ID: String): List<NutrientOfRecipeNetwork> {
        return listOf()
    }

    override fun getIngredients(ID: String): List<IngredientOfRecipeNetwork> {
        return listOf()
    }
}