package com.example.foodinfo.data.remote.retrofit

import com.example.foodinfo.domain.model.EdamamCredentials
import com.example.foodinfo.utils.edamam.EdamamInfo
import com.example.foodinfo.utils.edamam.FieldSet
import com.example.foodinfo.utils.extensions.trimMultiline


/**
 * ###Example:
 * Line breaks added for better readability
 * ~~~
 * a51e4a3e51e183371157d8705ca36bc7
 * ?type=public
 * &app_id=f8452af5
 * &app_key=0f6552d886aed96d8608d6be1f2fe6ae
 * &field=uri
 * &field=label
 * &field=image
 * &field=source
 * &field=url
 * &field=yield
 * ...
 * ~~~
 */
internal object EdamamRecipeURL {
    /**
     * Generates query to Edamam API to fetch single recipe.
     *
     * @param recipeID ID of the recipe that should be fetched.
     * @param fieldSet Recipe fields that should be included into result.
     * @param apiCredentials Object that contains app ID and Key to access Edamam API.
     */
    fun build(
        apiCredentials: EdamamCredentials,
        fieldSet: FieldSet,
        recipeID: String,
    ): String {
        return """
        $recipeID
        ?${EdamamInfo.APP_ID_FIELD}=${apiCredentials.appIDRecipes}
        &${EdamamInfo.APP_KEY_FIELD}=${apiCredentials.appKeyRecipes}
        &${EdamamInfo.RECIPE_TYPE_FIELD}=${EdamamInfo.RECIPE_TYPE_PUBLIC}
        ${fieldSet.fields}
        """.trimMultiline()
    }
}