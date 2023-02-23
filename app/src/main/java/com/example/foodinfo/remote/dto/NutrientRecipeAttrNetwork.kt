package com.example.foodinfo.remote.dto

import com.google.gson.annotations.SerializedName


data class NutrientRecipeAttrNetwork(
    @SerializedName("ID")
    val ID: Int,

    @SerializedName("tag")
    val tag: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("previewURL")
    val previewURL: String,

    @SerializedName("measure")
    val measure: String,

    @SerializedName("hasRDI")
    val hasRDI: Boolean,

    @SerializedName("dailyAllowance")
    val dailyAllowance: Float,

    @SerializedName("rangeMin")
    val rangeMin: Float,

    @SerializedName("rangeMax")
    val rangeMax: Float,

    @SerializedName("stepSize")
    val stepSize: Float
)