package com.example.foodinfo.features.search_filter.model

import com.example.foodinfo.core.ui.base.adapter.AppViewHolderModel


data class NutrientEditVHModel(
    val ID: Int,
    val infoID: Int,
    val name: String,
    val measure: String,
    val stepSize: Float,
    val rangeMin: Float,
    val rangeMax: Float,
    val minValue: Float?,
    val maxValue: Float?
) : AppViewHolderModel {

    override fun areItemsTheSame(other: AppViewHolderModel): Boolean =
        other is NutrientEditVHModel &&
        this.ID == other.ID

    override fun areContentsTheSame(other: AppViewHolderModel): Boolean =
        other is NutrientEditVHModel &&
        this.infoID == other.infoID &&
        this.name == other.name &&
        this.measure == other.measure &&
        this.stepSize == other.stepSize &&
        this.rangeMin == other.rangeMin &&
        this.rangeMax == other.rangeMax &&
        this.minValue == other.minValue &&
        this.maxValue == other.maxValue
}