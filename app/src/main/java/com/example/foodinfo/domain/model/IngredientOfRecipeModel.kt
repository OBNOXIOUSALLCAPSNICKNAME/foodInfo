package com.example.foodinfo.domain.model


data class IngredientOfRecipeModel(
    val ID: Int,
    val text: String,
    val measure: String,
    val quantity: Float,
    val weight: Float,
    val food: String,
    val foodCategory: String,
    val foodId: String,
    val previewURL: String
) {

    override fun equals(other: Any?) =
        other is IngredientOfRecipeModel &&
        this.text == other.text &&
        this.measure == other.measure &&
        this.quantity == other.quantity &&
        this.weight == other.weight &&
        this.food == other.food &&
        this.foodCategory == other.foodCategory &&
        this.foodId == other.foodId &&
        this.previewURL == other.previewURL

    override fun hashCode(): Int {
        var result = text.hashCode()
        result = 31 * result + measure.hashCode()
        result = 31 * result + quantity.hashCode()
        result = 31 * result + weight.hashCode()
        result = 31 * result + food.hashCode()
        result = 31 * result + foodCategory.hashCode()
        result = 31 * result + foodId.hashCode()
        result = 31 * result + previewURL.hashCode()
        return result
    }
}