package com.example.foodinfo.repository.model

import androidx.recyclerview.widget.DiffUtil


data class CategoryOfRecipeModel(
    val name: String,
    val labels: List<LabelShortModel>
) {

    object ItemCallBack :
        DiffUtil.ItemCallback<CategoryOfRecipeModel>() {

        override fun areItemsTheSame(
            oldItem: CategoryOfRecipeModel,
            newItem: CategoryOfRecipeModel
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: CategoryOfRecipeModel,
            newItem: CategoryOfRecipeModel
        ): Boolean {
            return oldItem.labels == newItem.labels
        }
    }
}
