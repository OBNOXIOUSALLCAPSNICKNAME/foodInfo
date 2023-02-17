package com.example.foodinfo.repository.model


data class SearchFilterEditModel(
    val name: String,
    val basics: List<BasicOfSearchFilterEditModel>,
    val nutrients: List<NutrientOfSearchFilterPreviewModel>,
    val categories: List<CategoryOfSearchFilterPreviewModel>
)