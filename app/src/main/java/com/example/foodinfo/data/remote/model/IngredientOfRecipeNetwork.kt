package com.example.foodinfo.data.remote.model

import com.google.gson.annotations.SerializedName


data class IngredientOfRecipeNetwork(
    @SerializedName(Fields.TEXT)
    val text: String,

    @SerializedName(Fields.QUANTITY)
    val quantity: Float,

    @SerializedName(Fields.MEASURE)
    val measure: String?,

    @SerializedName(Fields.FOOD)
    val food: String,

    @SerializedName(Fields.WEIGHT)
    val weight: Float,

    @SerializedName(Fields.FOOD_CATEGORY)
    val foodCategory: String?,

    @SerializedName(Fields.FOOD_ID)
    val foodID: String,

    @SerializedName(Fields.IMAGE)
    val image: String?
) {

    object Fields {
        const val TEXT = "text"
        const val QUANTITY = "quantity"
        const val MEASURE = "measure"
        const val FOOD = "food"
        const val WEIGHT = "weight"
        const val FOOD_CATEGORY = "foodCategory"
        const val FOOD_ID = "foodId"
        const val IMAGE = "image"
    }
}