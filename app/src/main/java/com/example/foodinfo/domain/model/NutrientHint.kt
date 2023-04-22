package com.example.foodinfo.domain.model

import com.example.foodinfo.core.utils.glide.svg.SVGModel


data class NutrientHint(
    val ID: Int,
    val name: String,
    val description: String,
    val preview: SVGModel
)