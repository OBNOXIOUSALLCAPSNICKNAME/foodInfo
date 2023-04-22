package com.example.foodinfo.features.category.mapper

import com.example.foodinfo.domain.model.LabelOfRecipeMetadata
import com.example.foodinfo.features.category.model.LabelVHModel
import com.example.foodinfo.utils.glide.svg.SVGModel


fun LabelOfRecipeMetadata.toVHModel(): LabelVHModel {
    return LabelVHModel(
        ID = this.ID,
        name = this.name,
        preview = SVGModel(this.previewURL)
    )
}