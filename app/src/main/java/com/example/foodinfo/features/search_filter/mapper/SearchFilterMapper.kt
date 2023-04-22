package com.example.foodinfo.features.search_filter.mapper

import com.example.foodinfo.domain.model.BasicOfSearchFilter
import com.example.foodinfo.domain.model.SearchFilter
import com.example.foodinfo.features.search_filter.model.SearchFilterModel


fun SearchFilter.toVHModel(): SearchFilterModel {
    return SearchFilterModel(
        name = this.name,
        basics = this.basics.map(BasicOfSearchFilter::toVHModelEdit),
        nutrients = this.nutrients.toVHModelPreview(),
        categories = this.categories.toVHModelPreview()
    )
}