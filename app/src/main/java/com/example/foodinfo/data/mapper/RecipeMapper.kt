package com.example.foodinfo.data.mapper

import com.example.foodinfo.data.local.model.*
import com.example.foodinfo.data.remote.model.*
import com.example.foodinfo.domain.model.Recipe
import com.example.foodinfo.domain.model.RecipeExtended
import com.example.foodinfo.domain.model.RecipeMetadata


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
        energy = this.nutrients.find { it.infoID == 1 }!!.toModel(),
        protein = this.nutrients.find { it.infoID == 12 }!!.toModel(),
        carb = this.nutrients.find { it.infoID == 7 }!!.toModel(),
        fat = this.nutrients.find { it.infoID == 2 }!!.toModel()
    )
}


fun RecipeMetadataNetwork.toDB(): RecipeMetadataDB {
    return RecipeMetadataDB(
        basics = this.basics.map(BasicOfRecipeMetadataNetwork::toDB),
        labels = this.labels.map(LabelOfRecipeMetadataNetwork::toDB),
        categories = this.categories.map(CategoryOfRecipeMetadataNetwork::toDB),
        nutrients = this.nutrients.map(NutrientOfRecipeMetadataNetwork::toDB)
    )
}

fun RecipeMetadataDB.toModel(): RecipeMetadata {
    return RecipeMetadata(
        basics = this.basics.map(BasicOfRecipeMetadataDB::toModel),
        labels = this.labels.map(LabelOfRecipeMetadataDB::toModel),
        nutrients = this.nutrients.map(NutrientOfRecipeMetadataDB::toModel),
        categories = this.categories.map(CategoryOfRecipeMetadataDB::toModel),
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

fun RecipeNetwork.toDBSave(metadata: RecipeMetadata): RecipeToSaveDB {
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
        nutrients = this.nutrients!!.toDB(recipeID, metadata.nutrients),
        labels = listOfNotNull(
            this.meal,
            this.diet,
            this.dish,
            this.health,
            this.cuisine
        ).flatten().toDB(recipeID, metadata.labels)
    )
}