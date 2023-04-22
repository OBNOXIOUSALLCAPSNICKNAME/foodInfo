package com.example.foodinfo.features.recipe.model

import com.example.foodinfo.core.ui.base.adapter.AppViewHolderModel


data class CategoryVHModel(
    val name: String,
    val labels: List<LabelModel>
) : AppViewHolderModel {
    override fun areItemsTheSame(other: AppViewHolderModel): Boolean =
        other is CategoryVHModel &&
        this.name == other.name

    override fun areContentsTheSame(other: AppViewHolderModel): Boolean =
        other is CategoryVHModel &&
        this.labels == other.labels
}