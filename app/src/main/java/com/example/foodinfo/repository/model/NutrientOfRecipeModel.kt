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
        ): Boolean {
            return oldItem.ID == newItem.ID
        }

        override fun areContentsTheSame(
            oldItem: NutrientOfRecipeModel,
            newItem: NutrientOfRecipeModel
        ): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.totalWeight == newItem.totalWeight &&
                    oldItem.dailyPercent == newItem.dailyPercent
        }
    }
}