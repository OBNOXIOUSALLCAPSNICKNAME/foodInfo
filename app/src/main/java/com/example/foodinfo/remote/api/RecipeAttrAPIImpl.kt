package com.example.foodinfo.remote.api

import com.example.foodinfo.remote.dto.*


class RecipeAttrAPIImpl : RecipeAttrAPI() {

    override fun getRecipeAttrs(): RecipeAttrsNetwork {
        return RecipeAttrsNetwork()
    }

    override fun getBasics(): List<BasicRecipeAttrNetwork> {
        return listOf()
    }

    override fun getLabels(): List<LabelRecipeAttrNetwork> {
        return listOf()
    }

    override fun getNutrients(): List<NutrientRecipeAttrNetwork> {
        return listOf()
    }

    override fun getCategories(): List<CategoryRecipeAttrNetwork> {
        return listOf()
    }
}