package com.example.foodinfo.repository.model

import androidx.recyclerview.widget.DiffUtil


data class LabelShortModel(
    val infoID: Int,
    val name: String
) {

    object ItemCallBack :
        DiffUtil.ItemCallback<LabelShortModel>() {

        override fun areItemsTheSame(
            oldItem: LabelShortModel,
            newItem: LabelShortModel
        ) =
            oldItem.infoID == newItem.infoID

        override fun areContentsTheSame(
            oldItem: LabelShortModel,
            newItem: LabelShortModel
        ) =
            oldItem.name == newItem.name
    }
}