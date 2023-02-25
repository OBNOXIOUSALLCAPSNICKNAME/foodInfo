package com.example.foodinfo.remote.dto

import com.google.gson.annotations.SerializedName


data class IngredientOfRecipeNetwork(
    @SerializedName("text")
    val text: String,

    @SerializedName("quantity")
    val quantity: Float,

    @SerializedName("measure")
    val measure: String,

    @SerializedName("food")
    val food: String,

    @SerializedName("weight")
    val weight: Float,

    @SerializedName("foodCategory")
    val foodCategory: String,

    @SerializedName("foodId")
    val foodID: String,

    @SerializedName("image")
    val image: String
)