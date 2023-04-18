package com.example.foodinfo.domain.model


data class LabelOfSearchFilter(
    val ID: Int,
    val infoID: Int,
    val tag: String,
    val name: String,
    var isSelected: Boolean,
)