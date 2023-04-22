package com.example.foodinfo.features.search_filter.model

import com.example.foodinfo.core.ui.base.adapter.AppViewHolderModel


data class NutrientPreviewVHModel(
    val ID: Int,
    val name: String,
    val range: String
) : AppViewHolderModel {

    override fun areItemsTheSame(other: AppViewHolderModel): Boolean =
        other is NutrientPreviewVHModel &&
        this.ID == other.ID

    override fun areContentsTheSame(other: AppViewHolderModel): Boolean =
        other is NutrientPreviewVHModel &&
        this.name == other.name &&
        this.range == other.range
}