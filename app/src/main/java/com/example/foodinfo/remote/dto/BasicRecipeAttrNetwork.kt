package com.example.foodinfo.remote.dto

import com.google.gson.annotations.SerializedName


data class BasicRecipeAttrNetwork(
    @SerializedName("ID")
    val ID: Int,

    @SerializedName("tag")
    val tag: String?,

    @SerializedName("name")
    val name: String,

    @SerializedName("columnName")
    val columnName: String,

    @SerializedName("measure")
    val measure: String,

    @SerializedName("stepSize")
    val stepSize: Float,

    @SerializedName("rangeMin")
    val rangeMin: Float,

    @SerializedName("rangeMax")
    val rangeMax: Float
)