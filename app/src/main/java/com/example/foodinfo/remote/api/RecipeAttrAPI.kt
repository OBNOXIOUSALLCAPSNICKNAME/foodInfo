package com.example.foodinfo.remote.api

import com.example.foodinfo.remote.dto.*


abstract class RecipeAttrAPI {

    abstract fun getRecipeAttrs(): RecipeAttrsNetwork

    abstract fun getBasics(): List<BasicRecipeAttrNetwork>

    abstract fun getLabels(): List<LabelRecipeAttrNetwork>

    abstract fun getNutrients(): List<NutrientRecipeAttrNetwork>

    abstract fun getCategory(ID: Int): CategoryRecipeAttrNetwork

    abstract fun getCategories(): List<CategoryRecipeAttrNetwork>
}