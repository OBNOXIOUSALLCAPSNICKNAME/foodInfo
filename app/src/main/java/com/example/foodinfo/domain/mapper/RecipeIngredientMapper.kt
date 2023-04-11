package com.example.foodinfo.domain.mapper

import com.example.foodinfo.local.model.IngredientOfRecipeDB
import com.example.foodinfo.remote.model.IngredientOfRecipeNetwork
import com.example.foodinfo.domain.model.IngredientOfRecipeModel


fun IngredientOfRecipeDB.toModel(): IngredientOfRecipeModel {
    return IngredientOfRecipeModel(
        ID = this.ID,
        text = this.text,
        measure = this.measure,
        quantity = this.quantity,
        weight = this.weight,
        food = this.food,
        foodId = this.foodID,
        foodCategory = this.foodCategory,
        previewURL = this.previewURL
    )
}

fun IngredientOfRecipeNetwork.toDB(recipeID: String): IngredientOfRecipeDB {
    return IngredientOfRecipeDB(
        recipeID = recipeID,
        text = this.text,
        quantity = this.quantity,
        measure = this.measure ?: IngredientOfRecipeDB.DEFAULT_MEASURE,
        weight = this.weight,
        food = this.food,
        foodCategory = this.foodCategory ?: IngredientOfRecipeDB.DEFAULT_CATEGORY,
        foodID = this.foodID,
        previewURL = this.image ?: IngredientOfRecipeDB.DEFAULT_PREVIEW,
    )
}