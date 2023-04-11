package com.example.foodinfo.remote.model

import com.google.gson.annotations.SerializedName


data class NutrientOfRecipeNetwork(
    @SerializedName(Fields.LABEL)
    val label: String,

    @SerializedName(Fields.QUANTITY)
    val quantity: Float,

    @SerializedName(Fields.UNIT)
    val unit: String
) {

    object Fields {
        const val LABEL = "label"
        const val QUANTITY = "quantity"
        const val UNIT = "unit"
    }
}