package com.example.foodinfo.features.search_filter.model

import com.example.foodinfo.ui.base.adapter.AppViewHolderModel


data class LabelEditVHModel(
    val ID: Int,
    val infoID: Int,
    val name: String,
    val isSelected: Boolean
) : AppViewHolderModel {

    override fun areItemsTheSame(other: AppViewHolderModel): Boolean =
        other is LabelEditVHModel &&
        this.ID == other.ID

    override fun areContentsTheSame(other: AppViewHolderModel): Boolean =
        other is LabelEditVHModel &&
        this.infoID == other.infoID &&
        this.name == other.name &&
        this.isSelected == other.isSelected
}