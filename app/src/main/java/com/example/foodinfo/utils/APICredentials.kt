package com.example.foodinfo.utils


class APICredentials(
    appIDFood: String = "bdd6ffae",
    appIDRecipes: String = "f8452af5",
    appIDNutrition: String = "10e0e928",
    appKeyFood: String = "d3a7dfa760ceea4112c6afe28adc2a99",
    appKeyRecipes: String = "0f6552d886aed96d8608d6be1f2fe6ae",
    appKeyNutrition: String = "906a807656e6c8ff4e4fdbfee1c80744",
) {
    val food: String
    val recipe: String
    val nutrition: String

    init {
        food = "?type=public&app_key=${appKeyFood}&app_id=${appIDFood}"
        recipe = "?type=public&app_key=${appKeyRecipes}&app_id=${appIDRecipes}"
        nutrition = "?type=public&app_key=${appKeyNutrition}&app_id=${appIDNutrition}"
    }
}