package com.example.foodinfo.repository.model

import androidx.recyclerview.widget.DiffUtil


data class NutrientOfRecipeModel(
    val ID: Int,
    val infoID: Int,
    val name: String,
    val measure: String,
    val totalWeight: Float,
    val dailyWeight: Float,
    val dailyPercent: Int,
) {

    object ItemCallBack :
        DiffUtil.ItemCallback<NutrientOfRecipeModel>() {

        override fun areItemsTheSame(
            oldItem: NutrientOfRecipeModel,
            newItem: NutrientOfRecipeModel
        ) =
            oldItem.ID == newItem.ID

        override fun areContentsTheSame(
            oldItem: NutrientOfRecipeModel,
            newItem: NutrientOfRecipeModel
        ) =
            oldItem.infoID == newItem.infoID &&
            oldItem.name == newItem.name &&
            oldItem.measure == newItem.measure &&
            oldItem.totalWeight == newItem.totalWeight &&
            oldItem.dailyWeight == newItem.dailyWeight &&
            oldItem.dailyPercent == newItem.dailyPercent
    }

    override fun equals(other: Any?) =
        other is NutrientOfRecipeModel &&
        this.infoID == other.infoID &&
        this.name == other.name &&
        this.measure == other.measure &&
        this.totalWeight == other.totalWeight &&
        this.dailyWeight == other.dailyWeight &&
        this.dailyPercent == other.dailyPercent

    override fun hashCode(): Int {
        var result = infoID
        result = 31 * result + name.hashCode()
        result = 31 * result + measure.hashCode()
        result = 31 * result + totalWeight.hashCode()
        result = 31 * result + dailyWeight.hashCode()
        result = 31 * result + dailyPercent
        return result
    }

}