package com.example.foodinfo.features.home.mapper

import com.example.foodinfo.domain.model.CategoryOfRecipeMetadata
import com.example.foodinfo.features.home.model.CategoryVHModel
import com.example.foodinfo.utils.glide.svg.SVGModel


fun CategoryOfRecipeMetadata.toVHModel(): CategoryVHModel {
    return CategoryVHModel(
        ID = this.ID,
        name = this.name,
        preview = SVGModel(this.previewURL)
    )
}