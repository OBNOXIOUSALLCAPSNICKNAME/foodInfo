package com.example.foodinfo.data.remote.model

import com.google.gson.annotations.SerializedName


data class RecipeNetwork(
    @SerializedName(Fields.URI)
    val URI: String? = null,

    @SerializedName(Fields.URL)
    val URL: String? = null,

    @SerializedName(Fields.SOURCE)
    val source: String? = null,

    @SerializedName(Fields.LABEL)
    val label: String? = null,

    @SerializedName(Fields.IMAGE)
    val image: String? = null,

    @SerializedName(Fields.SERVINGS)
    val servings: Float? = null,

    @SerializedName(Fields.CALORIES)
    val calories: Float? = null,

    @SerializedName(Fields.WEIGHT)
    val weight: Float? = null,

    @SerializedName(Fields.TIME)
    val time: Float? = null,

    @SerializedName(Fields.MEAL)
    val meal: List<String>? = null,

    @SerializedName(Fields.DISH)
    val dish: List<String>? = null,

    @SerializedName(Fields.DIET)
    val diet: List<String>? = null,

    @SerializedName(Fields.HEALTH)
    val health: List<String>? = null,

    @SerializedName(Fields.CUISINE)
    val cuisine: List<String>? = null,

    @SerializedName(Fields.TOTAL_NUTRIENTS)
    val nutrients: Map<String, NutrientOfRecipeNetwork>? = null,

    @SerializedName(Fields.INGREDIENTS)
    val ingredients: List<IngredientOfRecipeNetwork>? = null
) {

    object Fields {
        const val URI = "uri"
        const val URL = "url"
        const val SOURCE = "source"
        const val LABEL = "label"
        const val IMAGE = "image"
        const val SERVINGS = "yield"
        const val CALORIES = "calories"
        const val WEIGHT = "totalWeight"
        const val TIME = "totalTime"
        const val MEAL = "mealType"
        const val DISH = "dishType"
        const val DIET = "dietLabels"
        const val HEALTH = "healthLabels"
        const val CUISINE = "cuisineType"
        const val TOTAL_NUTRIENTS = "totalNutrients"
        const val INGREDIENTS = "ingredients"
    }

    companion object {
        const val RECIPE_BASE_URI = "http://www.edamam.com/ontologies/edamam.owl#recipe_"
    }
}
