package com.example.foodinfo.data.mapper

import com.example.foodinfo.data.local.model.EdamamCredentialsDB
import com.example.foodinfo.data.local.model.GitHubCredentialsDB
import com.example.foodinfo.domain.model.EdamamCredentials
import com.example.foodinfo.domain.model.GitHubCredentials


fun EdamamCredentials.toDB(): EdamamCredentialsDB {
    return EdamamCredentialsDB(
        name = this.name,
        appIDFood = this.appIDFood,
        appIDRecipes = this.appIDRecipes,
        appIDNutrition = this.appIDNutrition,
        appKeyFood = this.appKeyFood,
        appKeyRecipes = this.appKeyRecipes,
        appKeyNutrition = this.appKeyNutrition
    )
}

fun EdamamCredentialsDB.toModel(): EdamamCredentials {
    return EdamamCredentials(
        name = this.name,
        appIDFood = this.appIDFood,
        appIDRecipes = this.appIDRecipes,
        appIDNutrition = this.appIDNutrition,
        appKeyFood = this.appKeyFood,
        appKeyRecipes = this.appKeyRecipes,
        appKeyNutrition = this.appKeyNutrition
    )
}

fun GitHubCredentials.toDB(): GitHubCredentialsDB {
    return GitHubCredentialsDB(
        name = this.name,
        token = this.token
    )
}

fun GitHubCredentialsDB.toModel(): GitHubCredentials {
    return GitHubCredentials(
        name = this.name,
        token = this.token
    )
}