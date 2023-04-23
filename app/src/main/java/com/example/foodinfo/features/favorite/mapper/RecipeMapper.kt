package com.example.foodinfo.features.favorite.mapper

import com.example.foodinfo.domain.model.Recipe
import com.example.foodinfo.features.favorite.model.RecipeVHModel


fun Recipe.toVHModel(): RecipeVHModel {
    return RecipeVHModel(
        ID = this.ID,
        name = this.name,
        calories = this.calories.toString(),
        source = this.source,
        servings = this.servings.toString(),
        preview = preview
    )
}