package com.example.foodinfo.domain.model


data class CategoryOfSearchFilterEditModel(
    val name: String,
    val labels: List<LabelOfSearchFilterEditModel>
)
