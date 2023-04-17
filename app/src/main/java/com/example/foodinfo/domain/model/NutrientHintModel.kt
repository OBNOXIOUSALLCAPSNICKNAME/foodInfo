package com.example.foodinfo.domain.model

import com.example.foodinfo.utils.glide.svg.SVGModel


data class NutrientHintModel(
    val ID: Int,
    val label: String,
    val description: String,
    val preview: SVGModel
)