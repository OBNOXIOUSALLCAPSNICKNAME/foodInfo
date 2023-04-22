package com.example.foodinfo.features.recipe.model

import com.example.foodinfo.ui.base.adapter.AppViewHolderModel


data class NutrientVHModel(
    val ID: Int,
    val infoID: Int,
    val name: String,
    val weight: String,
    val dailyPercent: String,
    val dailyPercentValue: Int
) : AppViewHolderModel {

    override fun areItemsTheSame(other: AppViewHolderModel): Boolean =
        other is NutrientVHModel &&
        this.ID == other.ID

    override fun areContentsTheSame(other: AppViewHolderModel): Boolean =
        other is NutrientVHModel &&
        this.infoID == other.infoID &&
        this.name == other.name &&
        this.weight == other.weight &&
        this.dailyPercentValue == other.dailyPercentValue


    override fun equals(other: Any?) =
        other is NutrientVHModel &&
        this.infoID == other.infoID &&
        this.name == other.name &&
        this.weight == other.weight &&
        this.dailyPercent == other.dailyPercent &&
        this.dailyPercentValue == other.dailyPercentValue

    override fun hashCode(): Int {
        var result = infoID
        result = 31 * result + name.hashCode()
        result = 31 * result + weight.hashCode()
        result = 31 * result + dailyPercent.hashCode()
        result = 31 * result + dailyPercentValue
        return result
    }
}