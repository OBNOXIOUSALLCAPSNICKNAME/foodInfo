package com.example.foodinfo.data.mapper

import com.example.foodinfo.data.local.model.IngredientOfRecipeDB
import com.example.foodinfo.data.remote.model.IngredientOfRecipeNetwork
import com.example.foodinfo.domain.model.IngredientOfRecipe


fun IngredientOfRecipeDB.toModel(): IngredientOfRecipe {
    return IngredientOfRecipe(
        ID = this.ID,
        text = this.text,
        measure = this.measure,
        quantity = this.quantity,
        weight = this.weight,
        food = this.food,
        foodID = this.foodID,
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