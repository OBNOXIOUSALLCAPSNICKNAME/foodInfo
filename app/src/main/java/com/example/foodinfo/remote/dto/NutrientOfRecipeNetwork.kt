package com.example.foodinfo.remote.dto

import com.google.gson.annotations.SerializedName


data class NutrientOfRecipeNetwork(
    @SerializedName("label")
    val label: String,

    @SerializedName("quantity")
    val quantity: Float,

    @SerializedName("unit")
    val unit: String
)