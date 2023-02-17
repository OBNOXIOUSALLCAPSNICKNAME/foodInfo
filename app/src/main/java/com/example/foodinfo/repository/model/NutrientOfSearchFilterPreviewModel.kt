package com.example.foodinfo.repository.model

import androidx.recyclerview.widget.DiffUtil


data class NutrientOfSearchFilterPreviewModel(
    val ID: Int,
    val name: String,
    val measure: String,
    val minValue: Float?,
    val maxValue: Float?
) {

    object ItemCallBack :
        DiffUtil.ItemCallback<NutrientOfSearchFilterPreviewModel>() {

        override fun areItemsTheSame(
            oldItem: NutrientOfSearchFilterPreviewModel,
            newItem: NutrientOfSearchFilterPreviewModel
        ): Boolean {
            return oldItem.ID == newItem.ID
        }

        override fun areContentsTheSame(
            oldItem: NutrientOfSearchFilterPreviewModel,
            newItem: NutrientOfSearchFilterPreviewModel
        ): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.measure == newItem.measure &&
                    oldItem.minValue == newItem.minValue &&
                    oldItem.maxValue == newItem.maxValue
        }
    }
}