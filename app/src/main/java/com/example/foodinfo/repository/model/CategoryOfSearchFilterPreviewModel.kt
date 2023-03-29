package com.example.foodinfo.repository.model

import androidx.recyclerview.widget.DiffUtil


data class CategoryOfSearchFilterPreviewModel(
    val ID: Int,
    val name: String,
    val labels: List<LabelShortModel>
) {

    object ItemCallBack : DiffUtil.ItemCallback<CategoryOfSearchFilterPreviewModel>() {
        override fun areItemsTheSame(
            oldItem: CategoryOfSearchFilterPreviewModel,
            newItem: CategoryOfSearchFilterPreviewModel
        ) =
            oldItem.ID == newItem.ID

        override fun areContentsTheSame(
            oldItem: CategoryOfSearchFilterPreviewModel,
            newItem: CategoryOfSearchFilterPreviewModel
        ) =
            oldItem.name == newItem.name &&
            oldItem.labels == newItem.labels
    }
}
