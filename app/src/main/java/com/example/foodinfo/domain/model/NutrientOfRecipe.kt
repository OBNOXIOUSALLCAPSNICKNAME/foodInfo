package com.example.foodinfo.domain.model


data class NutrientOfRecipe(
    val ID: Int,
    val infoID: Int,
    val name: String,
    val measure: String,
    val precision: Int,
    val totalWeight: Float,
    val dailyWeight: Float,
    val dailyPercent: Int,
) {
    override fun equals(other: Any?): Boolean =
        other is NutrientOfRecipe &&
        this.infoID == other.infoID &&
        this.name == other.name &&
        this.measure == other.measure &&
        this.precision == other.precision &&
        this.totalWeight == other.totalWeight &&
        this.dailyWeight == other.dailyWeight &&
        this.dailyPercent == other.dailyPercent

    override fun hashCode(): Int {
        var result = infoID
        result = 31 * result + name.hashCode()
        result = 31 * result + measure.hashCode()
        result = 31 * result + precision.hashCode()
        result = 31 * result + totalWeight.hashCode()
        result = 31 * result + dailyWeight.hashCode()
        result = 31 * result + dailyPercent
        return result
    }
}