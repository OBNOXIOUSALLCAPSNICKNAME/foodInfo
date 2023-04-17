package com.example.foodinfo.domain.model

import com.example.foodinfo.local.model.RecipeAttrsDB


data class SearchFilterPresetModel(
    val basics: List<BasicOfFilterPresetModel> = listOf(),
    val nutrients: List<NutrientOfFilterPresetModel> = listOf(),
    val categories: List<CategoryOfFilterPresetModel> = listOf()
) {
    companion object {
        operator fun invoke(attrs: RecipeAttrsDB, labelID: Int): SearchFilterPresetModel {
            val label = attrs.labels.first { it.ID == labelID }
            val category = CategoryOfFilterPresetModel(
                tag = attrs.categories.first { it.ID == label.categoryID }.tag,
                labels = listOf(LabelOfFilterPresetModel(label.tag, label.ID))
            )
            return SearchFilterPresetModel(categories = listOf(category))
        }
    }
}