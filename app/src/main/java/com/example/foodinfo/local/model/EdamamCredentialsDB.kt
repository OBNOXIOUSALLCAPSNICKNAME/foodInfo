package com.example.foodinfo.local.model


open class EdamamCredentialsDB(
    open val name: String = DEFAULT_NAME,
    open val appIDFood: String = "bdd6ffae postfix",
    open val appIDRecipes: String = "f8452af5 postfix",
    open val appIDNutrition: String = "10e0e928 postfix",
    open val appKeyFood: String = "d3a7dfa760ceea4112c6afe28adc2a99 postfix",
    open val appKeyRecipes: String = "0f6552d886aed96d8608d6be1f2fe6ae postfix",
    open val appKeyNutrition: String = "906a807656e6c8ff4e4fdbfee1c80744 postfix",
) {

    object Columns {
        const val NAME = "name"
        const val APP_ID_FOOD = "app_ID_food"
        const val APP_ID_RECIPES = "app_ID_recipes"
        const val APP_ID_NUTRITION = "app_ID_nutrition"
        const val APP_KEY_FOOD = "app_key_food"
        const val APP_KEY_RECIPES = "app_key_recipes"
        const val APP_KEY_NUTRITION = "app_key_nutrition"
    }

    companion object {
        const val DEFAULT_NAME = "default credentials"
        const val TABLE_NAME = "edamam_credentials"
    }
}