package com.example.foodinfo.utils.edamam

import com.example.foodinfo.local.model.EdamamCredentialsDB
import com.example.foodinfo.remote.model.RecipeNetwork
import com.example.foodinfo.utils.extensions.trimMultiline
import com.example.foodinfo.utils.paging.EdamamPageURL


object EdamamInfo {
    const val PAGE_SIZE = 20
    const val THROTTLING_CALLS = 10
    const val THROTTLING_TIMER = 60
}

/**
 * Enum of Recipe fields that will tell [EdamamRecipeURL] and [EdamamPageURL] which fields should be included
 * in response to filter out unnecessary content and reduce network usage.
 */
enum class FieldSet(val fields: String) {
    FULL(
        """
        &field=${RecipeNetwork.Fields.URI}
        &field=${RecipeNetwork.Fields.URL}
        &field=${RecipeNetwork.Fields.SOURCE}
        &field=${RecipeNetwork.Fields.SHARE_AS}
        &field=${RecipeNetwork.Fields.LABEL}
        &field=${RecipeNetwork.Fields.IMAGE}
        &field=${RecipeNetwork.Fields.SERVINGS}
        &field=${RecipeNetwork.Fields.CALORIES}
        &field=${RecipeNetwork.Fields.WEIGHT}
        &field=${RecipeNetwork.Fields.TIME}
        &field=${RecipeNetwork.Fields.MEAL}
        &field=${RecipeNetwork.Fields.DISH}
        &field=${RecipeNetwork.Fields.DIET}
        &field=${RecipeNetwork.Fields.HEALTH}
        &field=${RecipeNetwork.Fields.CUISINE}
        &field=${RecipeNetwork.Fields.INGREDIENTS}
        &field=${RecipeNetwork.Fields.TOTAL_NUTRIENTS}
        """.trimMultiline()
    ),
    BASIC(
        """
        &field=${RecipeNetwork.Fields.URI}
        &field=${RecipeNetwork.Fields.URL}
        &field=${RecipeNetwork.Fields.SOURCE}
        &field=${RecipeNetwork.Fields.LABEL}
        &field=${RecipeNetwork.Fields.IMAGE}
        &field=${RecipeNetwork.Fields.SERVINGS}
        &field=${RecipeNetwork.Fields.CALORIES}
        &field=${RecipeNetwork.Fields.WEIGHT}
        &field=${RecipeNetwork.Fields.TIME}
        &field=${RecipeNetwork.Fields.INGREDIENT_LINES}
        """.trimMultiline()
    ),
    NUTRIENTS(
        """
        &field=${RecipeNetwork.Fields.TOTAL_NUTRIENTS}
        """.trimMultiline()
    ),
    INGREDIENTS(
        """
        &field=${RecipeNetwork.Fields.INGREDIENTS}
        """.trimMultiline()
    )
}


val EdamamCredentialsDB.food: String
    get() {
        return "?type=public&app_id=${this.appIDFood}&app_key=${this.appKeyFood}"
    }

val EdamamCredentialsDB.recipe: String
    get() {
        return "?type=public&app_id=${this.appIDRecipes}&app_key=${this.appKeyRecipes}"
    }

val EdamamCredentialsDB.nutrition: String
    get() {
        return "?type=public&app_id=${this.appIDNutrition}&app_key=${this.appKeyNutrition}"
    }

