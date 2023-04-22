package com.example.foodinfo.features.search_filter.model


data class SearchFilterModel(
    val name: String,
    val basics: List<BasicEditVHModel>,
    val nutrients: List<NutrientPreviewVHModel>,
    val categories: List<CategoryPreviewVHModel>,
)