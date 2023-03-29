package com.example.foodinfo.repository.mapper

import com.example.foodinfo.local.dto.RecipeAttrsDB
import com.example.foodinfo.local.dto.RecipeDB
import com.example.foodinfo.local.dto.RecipeExtendedDB
import com.example.foodinfo.local.dto.RecipeToSaveDB
import com.example.foodinfo.remote.dto.RecipeAttrsNetwork
import com.example.foodinfo.remote.dto.RecipeNetwork
import com.example.foodinfo.repository.model.EdamamImageURL
import com.example.foodinfo.repository.model.RecipeExtendedModel
import com.example.foodinfo.repository.model.RecipeFavoriteModel
import com.example.foodinfo.repository.model.RecipeShortModel


fun RecipeDB.toModelShort(): RecipeShortModel {
    return RecipeShortModel(
        ID = this.ID,
        name = this.name,
        calories = this.calories.toString(),
        servings = this.servings.toString(),
        cookingTime = this.cookingTime,
        ingredientsCount = this.ingredientsCount.toString(),
        preview = EdamamImageURL.fromString(this.previewURL),
        isFavorite = this.isFavorite
    )
}

fun RecipeDB.toModelFavorite(): RecipeFavoriteModel {
    return RecipeFavoriteModel(
        ID = this.ID,
        name = this.name,
        source = this.source,
        calories = this.calories.toString(),
        servings = this.servings.toString(),
        preview = EdamamImageURL.fromString(this.previewURL)
    )
}

fun RecipeExtendedDB.toModelExtended(): RecipeExtendedModel {
    return RecipeExtendedModel(
        ID = this.ID,
        name = this.name,
        weight = this.weight,
        cookingTime = this.cookingTime,
        servings = this.servings,
        preview = EdamamImageURL.fromString(this.previewURL),
        isFavorite = this.isFavorite,
        ingredientsPreviews = this.ingredients.map { it.previewURL },
        categories = this.labels.toModelRecipe(),
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

fun RecipeNetwork.toDBSave(attrs: RecipeAttrsDB): RecipeToSaveDB {
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