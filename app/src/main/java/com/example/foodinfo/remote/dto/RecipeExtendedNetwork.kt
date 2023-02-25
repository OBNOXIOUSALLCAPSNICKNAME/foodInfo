package com.example.foodinfo.remote.dto

import com.google.gson.annotations.SerializedName


data class RecipeExtendedNetwork(
    @SerializedName("uri")
    val URI: String,

    @SerializedName("label")
    val label: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("source")
    val source: String,

    @SerializedName("url")
    val URL: String,

    @SerializedName("shareAs")
    val shareAs: String,

    @SerializedName("mealType")
    val meal: List<String>,

    @SerializedName("dishType")
    val dish: List<String>,

    @SerializedName("dietLabels")
    val diet: List<String>,

    @SerializedName("healthLabels")
    val health: List<String>,

    @SerializedName("cuisineType")
    val cuisine: List<String>,

    @SerializedName("calories")
    val calories: Float,

    @SerializedName("yield")
    val servings: Float,

    @SerializedName("totalWeight")
    val weight: Float,

    @SerializedName("totalTime")
    val time: Float,

    @SerializedName("ingredients")
    val ingredients: List<IngredientOfRecipeNetwork>,

    @SerializedName("totalNutrients")
    val nutrients: Map<String, NutrientOfRecipeNetwork>
)