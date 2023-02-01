package com.example.foodinfo.remote.api

import com.example.foodinfo.remote.dto.IngredientsOfRecipeNetwork
import com.example.foodinfo.remote.dto.LabelsOfRecipeNetwork
import com.example.foodinfo.remote.dto.NutrientsOfRecipeNetwork
import com.example.foodinfo.remote.dto.RecipeNetwork


interface RecipeAPI {

    fun getRecipe(ID: String): RecipeNetwork?

    fun getLabels(ID: String): LabelsOfRecipeNetwork?

    fun getNutrients(ID: String): NutrientsOfRecipeNetwork?

    fun getIngredients(ID: String): IngredientsOfRecipeNetwork?
}