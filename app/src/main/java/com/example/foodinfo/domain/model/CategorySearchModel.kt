package com.example.foodinfo.domain.model

import com.example.foodinfo.utils.glide.svg.SVGModel


data class CategorySearchModel(
    val ID: Int,
    val name: String,
    val preview: SVGModel
)