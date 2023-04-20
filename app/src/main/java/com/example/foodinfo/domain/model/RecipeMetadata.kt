package com.example.foodinfo.domain.model


data class RecipeMetadata(
    val basics: List<BasicOfRecipeMetadata>,
    val labels: List<LabelOfRecipeMetadata>,
    val nutrients: List<NutrientOfRecipeMetadata>,
    val categories: List<CategoryOfRecipeMetadata>
)