package com.example.foodinfo.repository.model

import androidx.recyclerview.widget.DiffUtil


data class LabelHintModel(
    val ID: Int,
    val name: String,
    val description: String,
    val preview: SVGModel
) {

    object ItemCallBack :
        DiffUtil.ItemCallback<LabelHintModel>() {

        override fun areItemsTheSame(
            oldItem: LabelHintModel,
            newItem: LabelHintModel
        ): Boolean {
            return oldItem.ID == newItem.ID
        }

        override fun areContentsTheSame(
            oldItem: LabelHintModel,
            newItem: LabelHintModel
        ): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.description == newItem.description &&
                    oldItem.preview.content == newItem.preview.content
        }
    }
}