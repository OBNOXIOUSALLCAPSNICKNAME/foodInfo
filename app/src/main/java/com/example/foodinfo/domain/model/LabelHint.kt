package com.example.foodinfo.domain.model

import com.example.foodinfo.utils.glide.svg.SVGModel


data class LabelHint(
    val ID: Int,
    val name: String,
    val description: String,
    val preview: SVGModel
)