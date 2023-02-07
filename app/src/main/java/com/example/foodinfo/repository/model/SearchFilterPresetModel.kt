package com.example.foodinfo.repository.model

import com.example.foodinfo.repository.model.filter_field.BasicOfFilterPreset
import com.example.foodinfo.repository.model.filter_field.CategoryOfFilterPreset
import com.example.foodinfo.repository.model.filter_field.NutrientOfFilterPreset


data class SearchFilterPresetModel(
    val basics: List<BasicOfFilterPreset>,
    val nutrients: List<NutrientOfFilterPreset>,
    val categories: List<CategoryOfFilterPreset>
)