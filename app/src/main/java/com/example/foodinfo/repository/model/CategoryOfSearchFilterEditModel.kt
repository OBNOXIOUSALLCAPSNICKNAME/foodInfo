package com.example.foodinfo.repository.model


data class CategoryOfSearchFilterEditModel(
    val name: String,
    val labels: List<LabelOfSearchFilterEditModel>
)
