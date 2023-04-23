package com.example.foodinfo.domain.model


data class LabelOfRecipe(
    val ID: Int,
    val infoID: Int,
    val name: String,
    val categoryName: String
) {
    override fun equals(other: Any?): Boolean =
        other is LabelOfRecipe &&
        this.infoID == other.infoID &&
        this.name == other.name &&
        this.categoryName == other.categoryName

    override fun hashCode(): Int {
        var result = infoID
        result = 31 * result + name.hashCode()
        result = 31 * result + categoryName.hashCode()
        return result
    }
}