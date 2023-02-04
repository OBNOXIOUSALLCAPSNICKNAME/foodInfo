package com.example.foodinfo.remote.api

import com.example.foodinfo.local.dto.LabelRecipeAttrExtendedDB
import com.example.foodinfo.local.dto.NutrientRecipeAttrDB
import com.example.foodinfo.local.dto.RecipeAttrsDB
import com.example.foodinfo.remote.dto.*


abstract class RecipeAPI {

    abstract fun getRecipes(ID: String): List<RecipeNetwork>

    abstract fun getRecipeExtended(ID: String, attrs: RecipeAttrsDB): RecipeExtendedNetwork?

    abstract fun getLabels(ID: String, attrs: List<LabelRecipeAttrExtendedDB>): List<LabelOfRecipeNetwork>

    abstract fun getNutrients(ID: String, attrs: List<NutrientRecipeAttrDB>): List<NutrientOfRecipeNetwork>

    abstract fun getIngredients(ID: String): List<IngredientOfRecipeNetwork>
}