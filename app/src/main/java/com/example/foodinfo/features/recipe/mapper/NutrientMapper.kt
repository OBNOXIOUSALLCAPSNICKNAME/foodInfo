package com.example.foodinfo.features.recipe.mapper

import com.example.foodinfo.core.utils.RecipeMetadataUtils
import com.example.foodinfo.domain.model.NutrientOfRecipe
import com.example.foodinfo.features.recipe.model.NutrientModel
import com.example.foodinfo.features.recipe.model.NutrientVHModel


fun NutrientOfRecipe.toVHModel(): NutrientVHModel {
    return NutrientVHModel(
        ID = this.ID,
        infoID = this.infoID,
        name = this.name,
        weight = RecipeMetadataUtils.mapToGoal(
            value = this.totalWeight,
            goal = this.dailyWeight,
            step = this.stepSize,
            measure = this.measure
        ),
        dailyPercent = "${this.dailyPercent}%",
        dailyPercentValue = this.dailyPercent
    )
}

fun NutrientOfRecipe.toModel(): NutrientModel {
    return NutrientModel(
        ID = this.ID,
        name = this.name,
        weight = RecipeMetadataUtils.roundToStep(
            step = this.stepSize,
            value = this.totalWeight
        ),
        dailyPercentValue = this.dailyPercent
    )
}