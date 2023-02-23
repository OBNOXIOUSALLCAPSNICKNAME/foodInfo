package com.example.foodinfo.remote.dto

import com.google.gson.annotations.SerializedName


data class RecipeAttrsNetwork(
    @SerializedName("basics")
    val basics: List<BasicRecipeAttrNetwork>,

    @SerializedName("labels")
    val labels: List<LabelRecipeAttrNetwork>,

    @SerializedName("categories")
    val categories: List<CategoryRecipeAttrNetwork>,

    @SerializedName("nutrients")
    val nutrients: List<NutrientRecipeAttrNetwork>
)