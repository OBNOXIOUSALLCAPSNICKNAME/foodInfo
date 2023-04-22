package com.example.foodinfo.features.explore.mapper

import com.example.foodinfo.core.utils.RecipeMetadataUtils
import com.example.foodinfo.domain.model.Recipe
import com.example.foodinfo.features.explore.model.RecipeVHModel


fun Recipe.toVHModel(): RecipeVHModel {
    return RecipeVHModel(
        ID = this.ID,
        name = this.name,
        calories = this.calories.toString(),
        servings = this.servings.toString(),
        cookingTime = RecipeMetadataUtils.mapCookingTime(this.cookingTime),
        preview = this.preview.toString(),
        isFavorite = this.isFavorite
    )
}