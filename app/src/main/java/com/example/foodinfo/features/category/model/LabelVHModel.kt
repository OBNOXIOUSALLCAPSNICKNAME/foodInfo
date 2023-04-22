package com.example.foodinfo.features.category.model

import com.example.foodinfo.core.ui.base.adapter.AppViewHolderModel
import com.example.foodinfo.core.utils.glide.svg.SVGModel


data class LabelVHModel(
    val ID: Int,
    val name: String,
    val preview: SVGModel
) : AppViewHolderModel {

    override fun areItemsTheSame(other: AppViewHolderModel): Boolean =
        other is LabelVHModel &&
        this.ID == other.ID

    override fun areContentsTheSame(other: AppViewHolderModel): Boolean =
        other is LabelVHModel &&
        this.name == other.name &&
        this.preview == other.preview
}