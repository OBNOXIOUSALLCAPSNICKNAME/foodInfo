package com.example.foodinfo.features.search_filter.model

import com.example.foodinfo.ui.base.adapter.AppViewHolderModel


data class CategoryPreviewVHModel(
    val ID: Int,
    val name: String,
    val labels: List<LabelPreviewModel>
) : AppViewHolderModel {
    override fun areItemsTheSame(other: AppViewHolderModel): Boolean =
        other is CategoryPreviewVHModel &&
        this.ID == other.ID

    override fun areContentsTheSame(other: AppViewHolderModel): Boolean =
        other is CategoryPreviewVHModel &&
        this.name == other.name &&
        this.labels == other.labels
}