package com.example.foodinfo.data.mapper

import com.example.foodinfo.data.local.model.SearchFilterExtendedDB
import com.example.foodinfo.domain.model.SearchFilter
import com.example.foodinfo.domain.model.SearchFilterPreset


fun SearchFilterExtendedDB.toModel(): SearchFilter {
    return SearchFilter(
        name = this.name,
        basics = this.basics.toModel(),
        nutrients = this.nutrients.toModel(),
        categories = this.labels.toModel()
    )
}

fun SearchFilterExtendedDB.toModelPreset(): SearchFilterPreset {
    return SearchFilterPreset(
        basics = this.basics.toModelPreset(),
        nutrients = this.nutrients.toModelPreset(),
        categories = this.labels.toModelPreset()
    )
}