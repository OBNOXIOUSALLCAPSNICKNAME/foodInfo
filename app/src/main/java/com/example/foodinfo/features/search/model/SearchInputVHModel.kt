package com.example.foodinfo.features.search.model

import com.example.foodinfo.core.ui.base.adapter.AppViewHolderModel


data class SearchInputVHModel(
    val ID: Int,
    val date: String,
    val inputText: String
) : AppViewHolderModel {

    override fun areItemsTheSame(other: AppViewHolderModel): Boolean =
        other is SearchInputVHModel &&
        this.ID == other.ID

    override fun areContentsTheSame(other: AppViewHolderModel): Boolean =
        other is SearchInputVHModel &&
        this.inputText == other.inputText
}