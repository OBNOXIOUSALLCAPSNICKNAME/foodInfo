package com.example.foodinfo.domain.model

import androidx.recyclerview.widget.DiffUtil
import com.example.foodinfo.utils.glide.svg.SVGModel


data class NutrientHintModel(
    val ID: Int,
    val label: String,
    val description: String,
    val preview: SVGModel
) {

    object ItemCallBack :
        DiffUtil.ItemCallback<NutrientHintModel>() {

        override fun areItemsTheSame(
            oldItem: NutrientHintModel,
            newItem: NutrientHintModel
        ) =
            oldItem.ID == newItem.ID

        override fun areContentsTheSame(
            oldItem: NutrientHintModel,
            newItem: NutrientHintModel
        ) =
            oldItem.label == newItem.label &&
            oldItem.description == newItem.description &&
            oldItem.preview.content == newItem.preview.content
    }
}