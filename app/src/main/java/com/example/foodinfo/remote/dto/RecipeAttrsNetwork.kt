package com.example.foodinfo.remote.dto

import com.google.gson.annotations.SerializedName


data class RecipeAttrsNetwork(
    @SerializedName(Fields.BASICS)
    val basics: List<BasicRecipeAttrNetwork>,

    @SerializedName(Fields.LABELS)
    val labels: List<LabelRecipeAttrNetwork>,

    @SerializedName(Fields.CATEGORIES)
    val categories: List<CategoryRecipeAttrNetwork>,

    @SerializedName(Fields.NUTRIENTS)
    val nutrients: List<NutrientRecipeAttrNetwork>
) {

    object Fields {
        const val BASICS = "basics"
        const val LABELS = "labels"
        const val CATEGORIES = "categories"
        const val NUTRIENTS = "nutrients"
    }
}