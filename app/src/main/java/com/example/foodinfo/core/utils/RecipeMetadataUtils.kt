package com.example.foodinfo.core.utils

import com.example.foodinfo.core.utils.extensions.toString


object RecipeMetadataUtils {
    private val MINUTES_RANGE = 0..600
    private val HOURS_RANGE = 601..10000

    private const val MINUTES = "min"
    private const val HOURS = "hour"
    private const val DAYS = "day"
    private const val MINUTES_IN_HOUR = 60
    private const val MINUTES_IN_DAY = 60 * 24

    private const val INGREDIENT_WEIGHT_PRECISION = 1

    private const val MEASURE_MILLIGRAMS = "mg"
    private const val MEASURE_MICROGRAMS = "µg"
    private const val MEASURE_GRAMS = "g"

    const val RECIPE_SERVINGS_MEASURE = "ppl"
    const val RECIPE_WEIGHT_MEASURE = "g"


    const val ID_ENERGY = 1
    const val ID_PROTEIN = 12
    const val ID_FAT = 7
    const val ID_CARB = 2


    fun mapCookingTime(value: Int): String {
        return when (value) {
            in MINUTES_RANGE -> "$value $MINUTES"
            in HOURS_RANGE   -> "${value / MINUTES_IN_HOUR} $HOURS"
            else             -> "${value / MINUTES_IN_DAY} $DAYS"
        }
    }

    fun mapIngredientWeight(value: Float): String {
        return "${value.toString(INGREDIENT_WEIGHT_PRECISION)}${withSpacer(MEASURE_GRAMS)}"
    }


    fun mapToGoal(value: Float, goal: Float, precision: Int, measure: String): String {
        return "${value.toString(precision)} / ${goal.toString(precision)}${withSpacer(measure)}"
    }

    fun mapToRange(minValue: Float?, maxValue: Float?, precision: Int, measure: String): String = when {
        minValue == null && maxValue == null -> {
            throw IllegalStateException("At least one value for [minValue, maxValue] must be not null")
        }
        minValue == null                     -> {
            "≤${maxValue!!.toString(precision)}${withSpacer(measure)}"
        }
        maxValue == null                     -> {
            "≥${minValue.toString(precision)}${withSpacer(measure)}"
        }
        else                                 -> {
            "${minValue.toString(precision)} ─ ${maxValue.toString(precision)}${withSpacer(measure)}"
        }
    }


    private fun withSpacer(measure: String): String = when (measure) {
        MEASURE_GRAMS,
        MEASURE_MILLIGRAMS,
        MEASURE_MICROGRAMS -> measure
        else               -> " $measure"
    }
}