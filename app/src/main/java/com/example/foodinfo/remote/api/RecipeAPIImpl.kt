package com.example.foodinfo.remote.api

import com.example.foodinfo.local.dto.LabelRecipeAttrExtendedDB
import com.example.foodinfo.local.dto.NutrientRecipeAttrDB
import com.example.foodinfo.local.dto.RecipeAttrsDB
import com.example.foodinfo.remote.dto.*


class RecipeAPIImpl : RecipeAPI() {

    override fun getRecipes(ID: String): List<RecipeNetwork> {
        return listOf()
    }

    override fun getRecipeExtended(ID: String, attrs: RecipeAttrsDB): RecipeExtendedNetwork? {
        return RecipeExtendedNetwork()
    }

    override fun getLabels(ID: String, attrs: List<LabelRecipeAttrExtendedDB>): List<LabelOfRecipeNetwork> {
        return listOf()
    }

    override fun getNutrients(ID: String, attrs: List<NutrientRecipeAttrDB>): List<NutrientOfRecipeNetwork> {
        return listOf()
    }

    override fun getIngredients(ID: String): List<IngredientOfRecipeNetwork> {
        return listOf()
    }
}