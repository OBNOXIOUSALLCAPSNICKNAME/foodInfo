package com.example.foodinfo.features.search_filter.mapper

import com.example.foodinfo.domain.model.CategoryOfSearchFilter
import com.example.foodinfo.domain.model.LabelOfSearchFilter
import com.example.foodinfo.features.search_filter.model.CategoryPreviewVHModel
import com.example.foodinfo.features.search_filter.model.LabelEditVHModel
import com.example.foodinfo.features.search_filter.model.LabelPreviewModel


fun List<CategoryOfSearchFilter>.toVHModelPreview(): List<CategoryPreviewVHModel> {
    return this.map { category ->
        CategoryPreviewVHModel(
            ID = category.ID,
            name = category.name,
            labels = category.labels.filter { it.isSelected }.map(LabelOfSearchFilter::toModelPreview)
        )
    }
}


fun LabelOfSearchFilter.toModelPreview(): LabelPreviewModel {
    return LabelPreviewModel(
        name = this.name
    )
}

fun LabelOfSearchFilter.toModelEdit(): LabelEditVHModel {
    return LabelEditVHModel(
        ID = this.ID,
        infoID = this.infoID,
        name = this.name,
        isSelected = this.isSelected
    )
}