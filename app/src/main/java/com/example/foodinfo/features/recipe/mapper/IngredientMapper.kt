package com.example.foodinfo.features.recipe.mapper

import com.example.foodinfo.domain.model.IngredientOfRecipe
import com.example.foodinfo.features.recipe.model.IngredientVHModel
import com.example.foodinfo.utils.RecipeMetadataUtils
import com.example.foodinfo.utils.extensions.round


fun IngredientOfRecipe.toVHModel(): IngredientVHModel {
    return IngredientVHModel(
        ID = this.ID,
        text = this.text,
        weight = "${this.weight.round()}${RecipeMetadataUtils.withSpacer(RecipeMetadataUtils.MEASURE_GRAMS)}",
        previewURL = this.previewURL
    )
}