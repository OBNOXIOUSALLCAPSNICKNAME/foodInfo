package com.example.foodinfo.domain.model


data class CategoryOfSearchFilterPreviewModel(
    val ID: Int,
    val name: String,
    val labels: List<LabelShortModel>
)
