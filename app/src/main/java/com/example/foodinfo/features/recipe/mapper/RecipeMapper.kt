package com.example.foodinfo.features.recipe.mapper

import com.example.foodinfo.core.utils.RecipeMetadataUtils
import com.example.foodinfo.domain.model.RecipeExtended
import com.example.foodinfo.features.recipe.model.RecipeModel


fun RecipeExtended.toModel(): RecipeModel {
    return RecipeModel(
        ID = this.ID,
        name = this.name,
        weight = "${this.weight} ${RecipeMetadataUtils.RECIPE_WEIGHT_MEASURE}",
        servings = "${this.servings} ${RecipeMetadataUtils.RECIPE_SERVINGS_MEASURE}",
        cookingTime = RecipeMetadataUtils.mapCookingTime(this.cookingTime),
        preview = this.preview.toString(),
        isFavorite = this.isFavorite,
        categories = this.labels.toVHModel(),
        energy = this.nutrients.find { it.infoID == RecipeMetadataUtils.ID_ENERGY }!!.toModel(),
        protein = this.nutrients.find { it.infoID == RecipeMetadataUtils.ID_PROTEIN }!!.toModel(),
        carb = this.nutrients.find { it.infoID == RecipeMetadataUtils.ID_CARB }!!.toModel(),
        fat = this.nutrients.find { it.infoID == RecipeMetadataUtils.ID_FAT }!!.toModel(),
        ingredientPreviews = this.ingredients.map { it.previewURL },
    )
}