package com.example.foodinfo.domain.model

import com.example.foodinfo.utils.glide.svg.SVGModel


data class LabelSearchModel(
    val ID: Int,
    val name: String,
    val preview: SVGModel
)