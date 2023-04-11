package com.example.foodinfo.utils.edamam

import com.example.foodinfo.local.model.EdamamCredentialsDB


/**
 * Remote query to Edamam API to fetch single recipe.
 *
 * All special symbols will be correctly translated
 * (e.g. space will be replaced with **'%20'** and **'+'** will be replaced with **%2B**).
 *
 * @param recipeID ID of the recipe that should be fetched.
 * @param fieldSet Recipe fields that should be included into result.
 * @param apiCredentials Object that contains app ID and Key to access Edamam API.
 */
class EdamamRecipeURL(
    recipeID: String,
    fieldSet: FieldSet,
    apiCredentials: EdamamCredentialsDB
) {
    val value: String = "$recipeID${apiCredentials.recipe}${fieldSet.fields}"
}