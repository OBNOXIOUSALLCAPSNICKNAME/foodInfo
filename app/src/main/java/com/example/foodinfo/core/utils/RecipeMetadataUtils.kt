package com.example.foodinfo.core.utils

import kotlin.math.roundToInt


object RecipeMetadataUtils {
    private val MINUTES_RANGE = 0..600
    private val HOURS_RANGE = 601..10000

    private const val MINUTES = "min"
    private const val HOURS = "hour"
    private const val DAYS = "day"
    private const val MINUTES_IN_HOUR = 60
    private const val MINUTES_IN_DAY = 60 * 24

    private const val MEASURE_MILLIGRAMS = "mg"
    private const val MEASURE_MICROGRAMS = "µg"
    const val MEASURE_GRAMS = "g"

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

    fun roundToStep(step: Float, value: Float): String {
        return when (val precision = getPrecision(step)) {
            1f   -> value.toInt().toString()
            else -> "${(value * precision).roundToInt() / precision}"
        }
    }

    fun mapToGoal(value: Float, goal: Float, step: Float, measure: String): String {
        return "${roundToStep(step, value)} / ${roundToStep(step, goal)}${withSpacer(measure)}"
    }

    fun mapToRange(minValue: Float?, maxValue: Float?, step: Float, measure: String): String = when {
        minValue == null && maxValue == null -> {
            throw IllegalStateException("At least one value for [minValue, maxValue] must be not null")
        }
        minValue == null                     -> {
            "≤${roundToStep(step, maxValue!!)}${withSpacer(measure)}"
        }
        maxValue == null                     -> {
            "≥${roundToStep(step, minValue)}${withSpacer(measure)}"
        }
        else                                 -> {
            "${roundToStep(step, minValue)} ─ ${roundToStep(step, maxValue)}${withSpacer(measure)}"
        }
    }

    fun withSpacer(measure: String): String = when (measure) {
        MEASURE_GRAMS,
        MEASURE_MILLIGRAMS,
        MEASURE_MICROGRAMS -> measure
        else               -> " $measure"
    }


    private fun getPrecision(value: Float): Float {
        var precision = 1f
        while (((value * precision) - (value * precision).toInt()) != 0f) {
            precision *= 10f
        }
        return precision
    }
}