package com.example.foodinfo.remote.dto

import com.google.gson.annotations.SerializedName


data class RecipeNetwork(
    @SerializedName("uri")
    val URI: String,

    @SerializedName("source")
    val source: String,

    @SerializedName("label")
    val label: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("yield")
    val servings: Int,

    @SerializedName("calories")
    val calories: Float,

    @SerializedName("totalWeight")
    val weight: Float,

    @SerializedName("totalTime")
    val time: Float,

    @SerializedName("ingredientLines")
    val ingredients: List<String>
)