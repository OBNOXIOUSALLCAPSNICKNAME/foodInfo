package com.example.foodinfo.features.home.model

import com.example.foodinfo.ui.base.adapter.AppViewHolderModel
import com.example.foodinfo.utils.glide.svg.SVGModel


data class CategoryVHModel(
    val ID: Int,
    val name: String,
    val preview: SVGModel
) : AppViewHolderModel {

    override fun areItemsTheSame(other: AppViewHolderModel): Boolean =
        other is CategoryVHModel &&
        this.ID == other.ID

    override fun areContentsTheSame(other: AppViewHolderModel): Boolean =
        other is CategoryVHModel &&
        this.name == other.name &&
        this.preview == other.preview
}