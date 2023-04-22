package com.example.foodinfo.data.remote.model

import com.google.gson.annotations.SerializedName


data class RecipeMetadataNetwork(
    @SerializedName(Fields.BASICS)
    val basics: List<BasicOfRecipeMetadataNetwork>,

    @SerializedName(Fields.LABELS)
    val labels: List<LabelOfRecipeMetadataNetwork>,

    @SerializedName(Fields.CATEGORIES)
    val categories: List<CategoryOfRecipeMetadataNetwork>,

    @SerializedName(Fields.NUTRIENTS)
    val nutrients: List<NutrientOfRecipeMetadataNetwork>
) {

    object Fields {
        const val BASICS = "basics"
        const val LABELS = "labels"
        const val CATEGORIES = "categories"
        const val NUTRIENTS = "nutrients"
    }
}