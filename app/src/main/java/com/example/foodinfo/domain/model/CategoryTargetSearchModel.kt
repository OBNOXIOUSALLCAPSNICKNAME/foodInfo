package com.example.foodinfo.domain.model


data class CategoryTargetSearchModel(
    val name: String,
    val labels: List<LabelSearchModel>
)