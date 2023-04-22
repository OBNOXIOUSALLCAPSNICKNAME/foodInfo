package com.example.foodinfo.features.search_filter.mapper

import com.example.foodinfo.core.utils.RecipeMetadataUtils
import com.example.foodinfo.domain.model.NutrientOfSearchFilter
import com.example.foodinfo.features.search_filter.model.NutrientEditVHModel
import com.example.foodinfo.features.search_filter.model.NutrientPreviewVHModel


fun List<NutrientOfSearchFilter>.toVHModelPreview(): List<NutrientPreviewVHModel> {
    return this.filterNot { it.minValue == null && it.maxValue == null }.map { nutrient ->
        NutrientPreviewVHModel(
            ID = nutrient.ID,
            name = nutrient.name,
            range = RecipeMetadataUtils.mapToRange(
                minValue = nutrient.minValue,
                maxValue = nutrient.maxValue,
                step = nutrient.stepSize,
                measure = nutrient.measure
            )
        )
    }
}

fun NutrientOfSearchFilter.toVHModelEdit(): NutrientEditVHModel {
    return NutrientEditVHModel(
        ID = this.ID,
        infoID = this.infoID,
        name = this.name,
        measure = this.measure,
        stepSize = this.stepSize,
        rangeMin = this.rangeMin,
        rangeMax = this.rangeMax,
        minValue = this.minValue,
        maxValue = this.maxValue
    )
}