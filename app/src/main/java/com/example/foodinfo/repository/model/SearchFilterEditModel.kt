package com.example.foodinfo.repository.model


data class SearchFilterEditModel(
    val name: String,
    val basics: List<BasicOfSearchFilterEditModel>,
    val nutrients: List<NutrientFilterPreviewModel>,
    val categories: List<CategoryOfSearchFilterPreviewModel>
)