package com.example.foodinfo.repository.model


data class CategoryOfSearchFilterPreviewModel(
    val ID: Int,
    val name: String,
    val labels: List<LabelShortModel>
)
