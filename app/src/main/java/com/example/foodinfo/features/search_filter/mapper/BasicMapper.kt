package com.example.foodinfo.features.search_filter.mapper

import com.example.foodinfo.domain.model.BasicOfSearchFilter
import com.example.foodinfo.features.search_filter.model.BasicEditVHModel


fun BasicOfSearchFilter.toVHModelEdit(): BasicEditVHModel {
    return BasicEditVHModel(
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