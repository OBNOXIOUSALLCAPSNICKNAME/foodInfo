package com.example.foodinfo.domain.model


data class SearchFilterPreset(
    val basics: List<BasicOfSearchFilterPreset> = emptyList(),
    val nutrients: List<NutrientOfSearchFilterPreset> = emptyList(),
    val categories: List<CategoryOfSearchFilterPreset> = emptyList()
) {
    companion object {
        operator fun invoke(attrs: RecipeAttrs, labelID: Int): SearchFilterPreset {
            val label = attrs.labels.first { it.ID == labelID }
            val category = CategoryOfSearchFilterPreset(
                tag = attrs.categories.first { it.ID == label.categoryID }.tag,
                labels = listOf(LabelOfSearchFilterPreset(label.ID, label.tag))
            )
            return SearchFilterPreset(categories = listOf(category))
        }
    }
}