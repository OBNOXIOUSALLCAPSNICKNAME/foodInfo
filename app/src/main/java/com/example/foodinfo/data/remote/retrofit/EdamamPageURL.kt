package com.example.foodinfo.data.remote.retrofit

import com.example.foodinfo.core.utils.edamam.EdamamInfo
import com.example.foodinfo.core.utils.edamam.FieldSet
import com.example.foodinfo.core.utils.extensions.toString
import com.example.foodinfo.core.utils.extensions.trimMultiline
import com.example.foodinfo.domain.model.EdamamCredentials
import com.example.foodinfo.domain.model.SearchFilterPreset


/**
 * All special symbols will be correctly translated
 * (e.g. space will be replaced with **'%20'** and **'+'** will be replaced with **%2B**).
 *
 * ###Example:
 * Line breaks added for better readability
 * ~~~
 * ?type=public
 * &app_id=f8452af5
 * &app_key=0f6552d886aed96d8608d6be1f2fe6ae
 * &q=beef
 * &ingr=13
 * &diet=balanced
 * &diet=high-protein
 * &health=kosher
 * &dishType=Biscuits%20and%20cookies
 * &dishType=Bread
 * &nutrients%5BCA%5D=50-100
 * &nutrients%5BCHOCDF%5D=10%2B
 * &field=uri
 * &field=label
 * &field=image
 * &field=source
 * &field=url
 * &field=yield
 * ...
 * ~~~
 */
internal object EdamamPageURL {
    private fun rangeFieldToRemoteQuery(
        tag: String,
        minValue: Float?,
        maxValue: Float?,
        precision: Int
    ): String {
        return when {
            minValue == null -> {
                "$tag=${maxValue!!.toString(precision)}"
            }
            maxValue == null -> {
                "$tag=${minValue.toString(precision)}"
            }
            else             -> {
                "$tag=${minValue.toString(precision)}-${maxValue.toString(precision)}"
            }
        }
    }

    private fun nutrientToRemoteQuery(
        tag: String,
        minValue: Float?,
        maxValue: Float?
    ): String {
        return when {
            minValue == null -> {
                "%5B$tag%5D=$maxValue"
            }
            maxValue == null -> {
                "%5B$tag%5D=$minValue%2B"
            }
            else             -> {
                "%5B$tag%5D=$minValue-$maxValue"
            }
        }
    }

    private fun inputTextToRemoteQuery(inputText: String): String {
        return if (inputText == "") {
            ""
        } else {
            "&q=${inputText.replace(" ", "%20")}"
        }
    }

    /**
     * Generates query to Edamam API to fetch paged recipes.
     *
     * @param apiCredentials Object that contains app ID and Key to access Edamam API.
     * @param filterPreset Filter preset object that will be used to build query.
     * @param inputText If specified, result will contain only those recipes which name contain provided string.
     * @param fieldSet Declares which recipe fields should be included into response.
     */
    fun build(
        apiCredentials: EdamamCredentials,
        filterPreset: SearchFilterPreset,
        inputText: String,
        fieldSet: FieldSet
    ): String {
        return """
        ?${EdamamInfo.APP_ID_FIELD}=${apiCredentials.appIDRecipes}
        &${EdamamInfo.APP_KEY_FIELD}=${apiCredentials.appKeyRecipes}
        &${EdamamInfo.RECIPE_TYPE_FIELD}=${EdamamInfo.RECIPE_TYPE_PUBLIC}
        ${fieldSet.fields}
        ${inputTextToRemoteQuery(inputText)}
        ${
            filterPreset.basics.filterNot { it.tag == null }.joinToString(separator = "") { field ->
                "&${
                    rangeFieldToRemoteQuery(
                        field.tag!!,
                        field.minValue,
                        field.maxValue,
                        field.precision
                    )
                }"
            }
        }
        ${
            filterPreset.nutrients.joinToString(separator = "") { field ->
                "&nutrients${
                    nutrientToRemoteQuery(
                        field.tag,
                        field.minValue,
                        field.maxValue
                    )
                }"
            }
        }
        ${
            filterPreset.categories.flatMap { category ->
                category.labels.map { category.tag to it.tag.replace(" ", "%20") }
            }.joinToString(separator = "") { (category, label) ->
                "&$category=$label"
            }
        }
        """.trimMultiline()
    }
}