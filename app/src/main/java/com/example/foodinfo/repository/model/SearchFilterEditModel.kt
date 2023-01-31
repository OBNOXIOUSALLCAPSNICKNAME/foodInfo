package com.example.foodinfo.repository.model

import com.example.foodinfo.local.dto.SearchFilterDB


data class SearchFilterEditModel(
    val ID: Int = 0,
    val name: String = SearchFilterDB.DEFAULT_NAME,
    val baseFields: List<BasicOfSearchFilterEditModel>,
    val categories: List<CategoryOfSearchFilterPreviewModel>,
    val nutrients: List<NutrientFilterPreviewModel>
)