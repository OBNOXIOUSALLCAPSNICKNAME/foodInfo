package com.example.foodinfo.repository.model


data class CategoryTargetSearchModel(
    val name: String,
    val labels: List<LabelSearchModel>
)