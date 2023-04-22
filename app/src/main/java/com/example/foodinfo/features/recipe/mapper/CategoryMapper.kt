package com.example.foodinfo.features.recipe.mapper

import com.example.foodinfo.domain.model.LabelOfRecipe
import com.example.foodinfo.features.recipe.model.CategoryVHModel
import com.example.foodinfo.features.recipe.model.LabelModel


fun LabelOfRecipe.toModel(): LabelModel {
    return LabelModel(
        infoID = this.infoID,
        name = this.name
    )
}

fun List<LabelOfRecipe>.toVHModel(): List<CategoryVHModel> {
    return this.groupBy { it.categoryName }.entries.map { (categoryName, labels) ->
        CategoryVHModel(
            name = categoryName,
            labels = labels.map(LabelOfRecipe::toModel)
        )
    }
}