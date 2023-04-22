package com.example.foodinfo.features.category.mapper

import com.example.foodinfo.core.utils.glide.svg.SVGModel
import com.example.foodinfo.domain.model.LabelOfRecipeMetadata
import com.example.foodinfo.features.category.model.LabelVHModel


fun LabelOfRecipeMetadata.toVHModel(): LabelVHModel {
    return LabelVHModel(
        ID = this.ID,
        name = this.name,
        preview = SVGModel(this.previewURL)
    )
}