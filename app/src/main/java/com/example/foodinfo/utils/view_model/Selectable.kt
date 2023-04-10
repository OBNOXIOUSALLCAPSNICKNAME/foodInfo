package com.example.foodinfo.utils.view_model


data class Selectable<T>(
    val model: T,
    var isSelected: Boolean
)