package com.example.foodinfo.repository.model

import androidx.recyclerview.widget.DiffUtil


data class CategorySearchModel(
    val ID: Int,
    val name: String,
    val preview: SVGModel
) {
    object ItemCallBack :
        DiffUtil.ItemCallback<CategorySearchModel>() {

        override fun areItemsTheSame(
            oldItem: CategorySearchModel,
            newItem: CategorySearchModel
        ) =
            oldItem.ID == newItem.ID

        override fun areContentsTheSame(
            oldItem: CategorySearchModel,
            newItem: CategorySearchModel
        ) =
            oldItem.name == newItem.name &&
            oldItem.preview.content == newItem.preview.content
    }
}