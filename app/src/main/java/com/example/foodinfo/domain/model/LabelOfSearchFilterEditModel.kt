package com.example.foodinfo.domain.model


data class LabelOfSearchFilterEditModel(
    val ID: Int,
    val infoID: Int,
    val name: String,
    var isSelected: Boolean
)