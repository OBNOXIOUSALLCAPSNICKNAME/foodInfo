package com.example.foodinfo.repository.model

import androidx.recyclerview.widget.DiffUtil


data class NutrientHintModel(
    val id: Long,
    val label: String,
    val description: String,
    val preview: SVGModel
) {

    object ItemCallBack :
        DiffUtil.ItemCallback<NutrientHintModel>() {

        override fun areItemsTheSame(
            oldItem: NutrientHintModel,
            newItem: NutrientHintModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: NutrientHintModel,
            newItem: NutrientHintModel
        ): Boolean {
            return oldItem.label == newItem.label &&
                    oldItem.description == newItem.description &&
                    oldItem.preview.content == newItem.preview.content
        }
    }
}