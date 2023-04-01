package com.example.foodinfo.utils.edamam


data class CookingTime(
    val value: Int,
    val measure: String
) {
    override fun toString(): String {
        return "$value $measure"
    }


    companion object {
        private const val MINUTES = "min"
        private const val HOURS = "hour"
        private const val DAYS = "day"
        private const val MINUTES_IN_HOUR = 60
        private const val MINUTES_IN_DAY = 60 * 24

        operator fun invoke(value: Int): CookingTime {
            return when (value) {
                in 0..600     -> {
                    CookingTime(
                        value = value,
                        measure = MINUTES
                    )
                }
                in 601..10000 -> {
                    CookingTime(
                        value = value / MINUTES_IN_HOUR,
                        measure = HOURS
                    )
                }
                else          -> {
                    CookingTime(
                        value = value / MINUTES_IN_DAY,
                        measure = DAYS
                    )
                }
            }
        }
    }
}