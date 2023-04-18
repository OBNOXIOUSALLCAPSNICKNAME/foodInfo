package com.example.foodinfo.data.mapper

import com.example.foodinfo.data.local.model.RecipeAttrsDB
import com.example.foodinfo.data.local.model.RecipeDB
import com.example.foodinfo.data.local.model.RecipeExtendedDB
import com.example.foodinfo.data.local.model.RecipeToSaveDB
import com.example.foodinfo.data.remote.model.RecipeAttrsNetwork
import com.example.foodinfo.data.remote.model.RecipeNetwork
import com.example.foodinfo.domain.model.Recipe
import com.example.foodinfo.domain.model.RecipeExtended
import com.example.foodinfo.domain.model.RecipeAttrs


fun RecipeDB.toModel(): Recipe {
    return Recipe(
        ID = this.ID,
        name = this.name,
        weight = this.weight,
        cookingTime = this.cookingTime,
        previewURL = this.previewURL,
        servings = this.servings,
        isFavorite = this.isFavorite
    )
}

fun RecipeExtendedDB.toModelExtended(): RecipeExtended {
    return RecipeExtended(
        ID = this.ID,
        name = this.name,
        weight = this.weight,
        cookingTime = this.cookingTime,
        servings = this.servings,
        preview = this.previewURL,
        isFavorite = this.isFavorite,
        ingredientsPreviews = this.ingredients.map { it.previewURL },
        categories = this.labels.toRecipeCategories(),
        energy = this.nutrients.findLast { it.infoID == 1 }!!.toModel(),
        protein = this.nutrients.findLast { it.infoID == 12 }!!.toModel(),
        carb = this.nutrients.findLast { it.infoID == 7 }!!.toModel(),
        fat = this.nutrients.findLast { it.infoID == 2 }!!.toModel()
    )
}


fun RecipeAttrsNetwork.toDB(): RecipeAttrsDB {
    return RecipeAttrsDB(
        basics = this.basics.map { it.toDB() },
        labels = this.labels.map { it.toDB() },
        categories = this.categories.map { it.toDB() },
        nutrients = this.nutrients.map { it.toDB() }
    )
}

fun RecipeAttrsDB.toModel(): RecipeAttrs {
    return RecipeAttrs(
        basics = this.basics.map { it.toModel() },
        labels = this.labels.map { it.toModel() },
        nutrients = this.nutrients.map { it.toModel() },
        categories = this.categories.map { it.toModel() }
    )
}

fun RecipeNetwork.toDB(): RecipeDB {
    return RecipeDB(
        ID = this.URI!!.replace(RecipeNetwork.RECIPE_BASE_URI, ""),
        source = this.source!!,
        name = this.label!!,
        previewURL = this.image!!,
        calories = this.calories!!.toInt(),
        ingredientsCount = this.ingredients!!.size,
        weight = this.weight!!.toInt(),
        cookingTime = this.time!!.toInt(),
        servings = this.servings!!.toInt()
    )
}

fun RecipeNetwork.toDBSave(attrs: RecipeAttrs): RecipeToSaveDB {
    val recipeID = this.URI!!.replace(RecipeNetwork.RECIPE_BASE_URI, "")

    return RecipeToSaveDB(
        ID = recipeID,
        source = this.source!!,
        name = this.label!!,
        previewURL = this.image!!,
        calories = this.calories!!.toInt(),
        ingredientsCount = this.ingredients!!.size,
        weight = this.weight!!.toInt(),
        cookingTime = this.time!!.toInt(),
        servings = this.servings!!.toInt(),
        ingredients = this.ingredients.map { it.toDB(recipeID) },
        nutrients = this.nutrients!!.toDB(recipeID, attrs.nutrients),
        labels = listOfNotNull(
            this.meal,
            this.diet,
            this.dish,
            this.health,
            this.cuisine
        ).flatten().toDB(recipeID, attrs.labels)
    )
}