package com.example.foodinfo.remote.api

import com.example.foodinfo.remote.dto.RecipeHitNetwork
import com.example.foodinfo.remote.dto.RecipePageNetwork
import com.example.foodinfo.utils.APICredentials


class RecipeAPIImpl : RecipeAPI() {

    override fun getRecipesInit(query: String): RecipePageNetwork {
        // to prevent overriding local DB with empty data (replace after implementing API)
        throw NullPointerException()
    }

    override fun getRecipesNext(href: String): RecipePageNetwork {
        // to prevent overriding local DB with empty data (replace after implementing API)
        throw NullPointerException()
    }

    override fun getRecipeExtended(ID: String, apiCredentials: APICredentials): RecipeHitNetwork {
        // to prevent overriding local DB with empty data (replace after implementing API)
        throw NullPointerException()
    }

    override fun getNutrients(ID: String, apiCredentials: APICredentials): RecipeHitNetwork {
        // to prevent overriding local DB with empty data (replace after implementing API)
        throw NullPointerException()
    }

    override fun getIngredients(ID: String, apiCredentials: APICredentials): RecipeHitNetwork {
        // to prevent overriding local DB with empty data (replace after implementing API)
        throw NullPointerException()
    }
}