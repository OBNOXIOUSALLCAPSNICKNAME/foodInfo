package com.example.foodinfo.domain.model


data class SearchFilterPresetModel(
    val basics: List<BasicOfFilterPresetModel> = listOf(),
    val nutrients: List<NutrientOfFilterPresetModel> = listOf(),
    val categories: List<CategoryOfFilterPresetModel> = listOf()
)