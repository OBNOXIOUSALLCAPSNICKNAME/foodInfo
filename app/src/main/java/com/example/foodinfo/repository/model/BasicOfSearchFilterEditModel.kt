package com.example.foodinfo.repository.model

import androidx.recyclerview.widget.DiffUtil


data class BasicOfSearchFilterEditModel(
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
        DiffUtil.ItemCallback<BasicOfSearchFilterEditModel>() {

        override fun areItemsTheSame(
            oldItem: BasicOfSearchFilterEditModel,
            newItem: BasicOfSearchFilterEditModel
        ): Boolean {
            return oldItem.ID == newItem.ID
        }

        override fun areContentsTheSame(
            oldItem: BasicOfSearchFilterEditModel,
            newItem: BasicOfSearchFilterEditModel
        ): Boolean {
            return oldItem.infoID == newItem.infoID &&
                    oldItem.name == newItem.name &&
                    oldItem.measure == newItem.measure &&
                    oldItem.stepSize == newItem.stepSize &&
                    oldItem.rangeMin == newItem.rangeMin &&
                    oldItem.rangeMax == newItem.rangeMax &&
                    oldItem.minValue == newItem.minValue &&
                    oldItem.maxValue == newItem.maxValue
        }
    }
}