package com.example.foodinfo.repository.model

import androidx.recyclerview.widget.DiffUtil


data class CategoryOfSearchFilterEditModel(
    val labels: List<LabelOfSearchFilterEditModel>
) {

    object ItemCallBack :
        DiffUtil.ItemCallback<CategoryOfSearchFilterEditModel>() {

        override fun areItemsTheSame(
            oldItem: CategoryOfSearchFilterEditModel,
            newItem: CategoryOfSearchFilterEditModel
        ): Boolean {
            return oldItem.labels == newItem.labels
        }

        override fun areContentsTheSame(
            oldItem: CategoryOfSearchFilterEditModel,
            newItem: CategoryOfSearchFilterEditModel
        ): Boolean {
            return oldItem.labels == newItem.labels
        }
    }
}
