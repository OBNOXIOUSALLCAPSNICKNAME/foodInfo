package com.example.foodinfo.remote.api

import com.example.foodinfo.remote.dto.*


class RecipeAPIImpl : RecipeAPI() {

    override fun getRecipes(ID: String): List<RecipeNetwork> {
        // to prevent overriding local DB with empty data (replace after implementing API)
        throw NullPointerException()
    }

    override fun getRecipeExtended(ID: String): RecipeExtendedNetwork {
        // to prevent overriding local DB with empty data (replace after implementing API)
        throw NullPointerException()
    }

    override fun getLabels(ID: String): List<LabelOfRecipeNetwork> {
        // to prevent overriding local DB with empty data (replace after implementing API)
        throw NullPointerException()
    }

    override fun getNutrients(ID: String): List<NutrientOfRecipeNetwork> {
        // to prevent overriding local DB with empty data (replace after implementing API)
        throw NullPointerException()
    }

    override fun getIngredients(ID: String): List<IngredientOfRecipeNetwork> {
        // to prevent overriding local DB with empty data (replace after implementing API)
        throw NullPointerException()
    }
}