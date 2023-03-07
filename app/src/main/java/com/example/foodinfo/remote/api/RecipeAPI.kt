package com.example.foodinfo.remote.api

import com.example.foodinfo.remote.dto.RecipeHitNetwork
import com.example.foodinfo.remote.dto.RecipePageNetwork
import com.example.foodinfo.utils.APICredentials

/*
    getNutrients and getIngredients returning RecipeHitNetwork may confuse but because of API has
    only two types of response: page and single hit, it will always return RecipeHitNetwork
    regardless of what fields needed. By default, API will return all recipe fields, to reduce the load on
    the network, request should contain only the required fields.
 */
interface RecipeAPI {

    fun getRecipesInit(query: String): RecipePageNetwork {
        // to prevent overriding local DB with empty data (replace after implementing API)
        throw NullPointerException()
    }

    fun getRecipesNext(href: String): RecipePageNetwork {
        // to prevent overriding local DB with empty data (replace after implementing API)
        throw NullPointerException()
    }

    fun getRecipeExtended(
        ID: String,
        apiCredentials: APICredentials = APICredentials()
    ): RecipeHitNetwork {
        // to prevent overriding local DB with empty data (replace after implementing API)
        throw NullPointerException()
    }

    fun getNutrients(
        ID: String,
        apiCredentials: APICredentials = APICredentials()
    ): RecipeHitNetwork {
        // to prevent overriding local DB with empty data (replace after implementing API)
        throw NullPointerException()
    }

    fun getIngredients(
        ID: String,
        apiCredentials: APICredentials = APICredentials()
    ): RecipeHitNetwork {
        // to prevent overriding local DB with empty data (replace after implementing API)
        throw NullPointerException()
    }
}