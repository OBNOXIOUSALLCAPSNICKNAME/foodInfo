package com.example.foodinfo.repository.model

import androidx.recyclerview.widget.DiffUtil


data class LabelOfSearchFilterEditModel(
    val ID: Int,
    val infoID: Int,
    val name: String,
    var isSelected: Boolean
) {

    object ItemCallBack :
        DiffUtil.ItemCallback<LabelOfSearchFilterEditModel>() {

        override fun areItemsTheSame(
            oldItem: LabelOfSearchFilterEditModel,
            newItem: LabelOfSearchFilterEditModel
        ) =
            oldItem.ID == newItem.ID

        override fun areContentsTheSame(
            oldItem: LabelOfSearchFilterEditModel,
            newItem: LabelOfSearchFilterEditModel
        ) =
            oldItem.infoID == newItem.infoID &&
            oldItem.name == newItem.name &&
            oldItem.isSelected == newItem.isSelected
    }
}