package com.example.foodinfo.features.home.mapper

import com.example.foodinfo.core.utils.glide.svg.SVGModel
import com.example.foodinfo.domain.model.CategoryOfRecipeMetadata
import com.example.foodinfo.features.home.model.CategoryVHModel


fun CategoryOfRecipeMetadata.toVHModel(): CategoryVHModel {
    return CategoryVHModel(
        ID = this.ID,
        name = this.name,
        preview = SVGModel(this.previewURL)
    )
}