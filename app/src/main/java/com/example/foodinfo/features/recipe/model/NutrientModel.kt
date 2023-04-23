package com.example.foodinfo.features.recipe.model


data class NutrientModel(
    val ID: Int,
    val name: String,
    val weight: String,
    val dailyPercentValue: Int
) {
    override fun equals(other: Any?): Boolean =
        other is NutrientModel &&
        this.name == other.name &&
        this.weight == other.weight &&
        this.dailyPercentValue == other.dailyPercentValue

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + weight.hashCode()
        result = 31 * result + dailyPercentValue
        return result
    }
}