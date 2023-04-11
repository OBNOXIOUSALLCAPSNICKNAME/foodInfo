package com.example.foodinfo.domain.mapper

import com.example.foodinfo.local.model.RecipeAttrsDB
import com.example.foodinfo.local.model.RecipeDB
import com.example.foodinfo.local.model.RecipeExtendedDB
import com.example.foodinfo.local.model.RecipeToSaveDB
import com.example.foodinfo.remote.model.RecipeAttrsNetwork
import com.example.foodinfo.remote.model.RecipeNetwork
import com.example.foodinfo.domain.model.RecipeExtendedModel
import com.example.foodinfo.domain.model.RecipeFavoriteModel
import com.example.foodinfo.domain.model.RecipeShortModel
import com.example.foodinfo.utils.edamam.CookingTime
import com.example.foodinfo.utils.edamam.EdamamImageURL


fun RecipeDB.toModelShort(): RecipeShortModel {
    return RecipeShortModel(
        ID = this.ID,
        name = this.name,
        calories = this.calories.toString(),
        servings = this.servings.toString(),
        cookingTime = CookingTime(this.cookingTime),
        ingredientsCount = this.ingredientsCount.toString(),
        preview = EdamamImageURL(this.previewURL),
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
        preview = EdamamImageURL(this.previewURL)
    )
}

fun RecipeExtendedDB.toModelExtended(): RecipeExtendedModel {
    return RecipeExtendedModel(
        ID = this.ID,
        name = this.name,
        weight = this.weight,
        cookingTime = CookingTime(this.cookingTime),
        servings = this.servings,
        preview = EdamamImageURL(this.previewURL),
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