package com.example.foodinfo.utils


object RecipeMetadataUtils {
    private const val MINUTES = "min"
    private const val HOURS = "hour"
    private const val DAYS = "day"
    private const val MINUTES_IN_HOUR = 60
    private const val MINUTES_IN_DAY = 60 * 24
    private val MINUTES_RANGE = 0..600
    private val HOURS_RANGE = 601..10000

    const val SERVINGS_MEASURE = "ppl"
    const val WEIGHT_MEASURE = "g"

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
        return if ((step - step.toInt()) == 0f) {
            value.toInt().toString()
        } else {
            "%.1f".format(value)
        }
    }
}