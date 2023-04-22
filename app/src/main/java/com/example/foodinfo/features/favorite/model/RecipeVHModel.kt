package com.example.foodinfo.features.favorite.model

import com.example.foodinfo.ui.base.adapter.AppViewHolderModel
import com.example.foodinfo.utils.view_model.Selectable


data class RecipeVHModel(
    override val ID: String,
    override var isSelected: Boolean = false,
    val name: String,
    val calories: String,
    val source: String,
    val servings: String,
    val preview: String
) : Selectable<String>, AppViewHolderModel {

    override fun areItemsTheSame(other: AppViewHolderModel): Boolean =
        other is RecipeVHModel &&
        this.ID == other.ID

    override fun areContentsTheSame(other: AppViewHolderModel): Boolean =
        other is RecipeVHModel &&
        this.name == other.name &&
        this.calories == other.calories &&
        this.source == other.source &&
        this.servings == other.servings &&
        this.preview == other.preview &&
        this.isSelected == other.isSelected


    override fun getChangePayload(other: AppViewHolderModel): Any? {
        other as RecipeVHModel
        if (this.isSelected != other.isSelected) return other.isSelected
        return super.getChangePayload(other)
    }
}