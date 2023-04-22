package com.example.foodinfo.data.remote.model

import com.google.gson.annotations.SerializedName


data class NutrientOfRecipeNetwork(
    @SerializedName(Fields.VALUE)
    val value: Float
) {

    object Fields {
        const val VALUE = "quantity"
    }
}