package com.example.foodinfo.domain.model


data class LabelOfRecipe(
    val ID: Int,
    val infoID: Int,
    val name: String
) {
    override fun equals(other: Any?) =
        other is LabelOfRecipe &&
        this.infoID == other.infoID &&
        this.name == other.name

    override fun hashCode(): Int {
        var result = infoID
        result = 31 * result + name.hashCode()
        return result
    }
}