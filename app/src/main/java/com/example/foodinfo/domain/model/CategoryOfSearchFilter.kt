package com.example.foodinfo.domain.model


data class CategoryOfSearchFilter(
    val tag: String,
    val name: String,
    val labels: List<LabelOfSearchFilter>
)