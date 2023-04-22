package com.example.foodinfo.features.recipe.model

import com.example.foodinfo.core.ui.base.adapter.AppViewHolderModel


data class IngredientVHModel(
    val ID: Int,
    val text: String,
    val weight: String,
    val previewURL: String
) : AppViewHolderModel {

    override fun areItemsTheSame(other: AppViewHolderModel): Boolean =
        other is IngredientVHModel &&
        this.ID == other.ID

    override fun areContentsTheSame(other: AppViewHolderModel): Boolean =
        other is IngredientVHModel &&
        this.text == other.text &&
        this.weight == other.weight &&
        this.previewURL == other.previewURL


    override fun equals(other: Any?) =
        other is IngredientVHModel &&
        this.text == other.text &&
        this.weight == other.weight &&
        this.previewURL == other.previewURL

    override fun hashCode(): Int {
        var result = text.hashCode()
        result = 31 * result + weight.hashCode()
        result = 31 * result + previewURL.hashCode()
        return result
    }
}