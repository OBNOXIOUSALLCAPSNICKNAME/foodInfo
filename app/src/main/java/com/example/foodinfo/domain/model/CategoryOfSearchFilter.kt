package com.example.foodinfo.domain.model


data class CategoryOfSearchFilter(
    val ID: Int,
    val tag: String,
    val name: String,
    val labels: List<LabelOfSearchFilter>
)