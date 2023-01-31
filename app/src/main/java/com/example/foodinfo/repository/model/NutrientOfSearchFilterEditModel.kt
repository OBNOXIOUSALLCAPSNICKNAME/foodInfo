package com.example.foodinfo.repository.model

import androidx.recyclerview.widget.DiffUtil


data class NutrientOfSearchFilterEditModel(
    val ID: Int,
    val infoID: Int,
    val name: String,
    val measure: String,
    val stepSize: Float,
    val rangeMin: Float,
    val rangeMax: Float,
    var minValue: Float,
    var maxValue: Float
) {

    object ItemCallBack :
        DiffUtil.ItemCallback<NutrientOfSearchFilterEditModel>() {

        override fun areItemsTheSame(
            oldItem: NutrientOfSearchFilterEditModel,
            newItem: NutrientOfSearchFilterEditModel
        ): Boolean {
            return oldItem.ID == newItem.ID
        }

        override fun areContentsTheSame(
            oldItem: NutrientOfSearchFilterEditModel,
            newItem: NutrientOfSearchFilterEditModel
        ): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.infoID == newItem.infoID &&
                    oldItem.measure == newItem.measure &&
                    oldItem.stepSize == newItem.stepSize &&
                    oldItem.rangeMin == newItem.rangeMin &&
                    oldItem.rangeMax == newItem.rangeMax &&
                    oldItem.minValue == newItem.minValue &&
                    oldItem.maxValue == newItem.maxValue
        }
    }
}