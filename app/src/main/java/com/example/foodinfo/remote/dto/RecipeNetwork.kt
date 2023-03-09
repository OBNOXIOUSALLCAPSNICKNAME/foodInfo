package com.example.foodinfo.remote.dto

import com.example.foodinfo.utils.trimMultiline
import com.google.gson.annotations.SerializedName


data class RecipeNetwork(
    @SerializedName(Fields.URI)
    val URI: String? = null,

    @SerializedName(Fields.URL)
    val URL: String? = null,

    @SerializedName(Fields.SOURCE)
    val source: String? = null,

    @SerializedName(Fields.SHARE_AS)
    val shareAs: String? = null,

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
    val ingredients: List<IngredientOfRecipeNetwork>? = null,

    @SerializedName(Fields.INGREDIENT_LINES)
    val ingredientsLines: List<String>? = null
) {

    object Fields {
        const val URI = "uri"
        const val URL = "url"
        const val SOURCE = "source"
        const val SHARE_AS = "shareAs"
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
        const val INGREDIENT_LINES = "ingredientLines"
    }

    object FieldSet {
        val BASIC: String = """
            &field=${Fields.URI}
            &field=${Fields.URL}
            &field=${Fields.SOURCE}
            &field=${Fields.LABEL}
            &field=${Fields.IMAGE}
            &field=${Fields.SERVINGS}
            &field=${Fields.INGREDIENT_LINES}
            &field=${Fields.CALORIES}
            &field=${Fields.WEIGHT}
            &field=${Fields.TIME}
        """.trimMultiline()

        val NUTRIENTS: String = """
            &field=${Fields.TOTAL_NUTRIENTS}
        """.trimMultiline()

        val INGREDIENTS: String = """
            &field=${Fields.INGREDIENTS}
            &field=${Fields.INGREDIENT_LINES}
        """.trimMultiline()
    }
}
