package com.example.foodinfo.domain.model


data class RecipeAttrs(
    val basics: List<BasicRecipeAttr>,
    val labels: List<LabelRecipeAttr>,
    val nutrients: List<NutrientRecipeAttr>,
    val categories: List<CategoryRecipeAttr>
)