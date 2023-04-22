package com.example.foodinfo.features.explore.model

import com.example.foodinfo.ui.base.adapter.AppViewHolderModel


data class RecipeVHModel(
    val ID: String,
    val name: String,
    val calories: String,
    val servings: String,
    val cookingTime: String,
    val preview: String,
    val isFavorite: Boolean
) : AppViewHolderModel {

    override fun areItemsTheSame(other: AppViewHolderModel): Boolean =
        other is RecipeVHModel &&
        this.ID == other.ID

    override fun areContentsTheSame(other: AppViewHolderModel): Boolean =
        other is RecipeVHModel &&
        this.name == other.name &&
        this.calories == other.calories &&
        this.servings == other.servings &&
        this.cookingTime == other.cookingTime &&
        this.preview == other.preview &&
        this.isFavorite == other.isFavorite

    override fun getChangePayload(other: AppViewHolderModel): Any? {
        other as RecipeVHModel
        if (this.isFavorite != other.isFavorite) return other.isFavorite
        return super.getChangePayload(other)
    }
}