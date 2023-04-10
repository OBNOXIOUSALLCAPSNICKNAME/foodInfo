package com.example.foodinfo.repository.model


data class LabelOfSearchFilterEditModel(
    val ID: Int,
    val infoID: Int,
    val name: String,
    var isSelected: Boolean
)