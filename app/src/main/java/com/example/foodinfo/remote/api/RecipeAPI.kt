package com.example.foodinfo.remote.api

import com.example.foodinfo.remote.dto.IngredientOfRecipeNetwork
import com.example.foodinfo.remote.dto.NutrientOfRecipeNetwork
import com.example.foodinfo.remote.dto.RecipeExtendedNetwork
import com.example.foodinfo.remote.dto.RecipeNetwork


abstract class RecipeAPI {

    abstract fun getRecipes(ID: String): List<RecipeNetwork>

    abstract fun getRecipeExtended(ID: String): RecipeExtendedNetwork?

    abstract fun getNutrients(ID: String): List<NutrientOfRecipeNetwork>

    abstract fun getIngredients(ID: String): List<IngredientOfRecipeNetwork>
}