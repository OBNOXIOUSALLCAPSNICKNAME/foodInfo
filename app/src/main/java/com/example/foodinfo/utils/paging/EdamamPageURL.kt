package com.example.foodinfo.utils.paging

import com.example.foodinfo.local.dto.EdamamCredentialsDB
import com.example.foodinfo.repository.model.SearchFilterPresetModel
import com.example.foodinfo.utils.edamam.FieldSet
import com.example.foodinfo.utils.edamam.recipe
import com.example.foodinfo.utils.extensions.trimMultiline


/**
 * Generates query to Edamam API to fetch paged recipes.
 *
 * All special symbols will be correctly translated
 * (e.g. space will be replaced with **'%20'** and **'+'** will be replaced with **%2B**).
 *
 * Result will contain all fields represented in [FieldSet.FULL] to be able to perform query from local DB.
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
 *
 * @param searchFilterPreset Filter preset object that will be used to build query.
 * @param inputText If specified, result will contain only those recipes which name contain provided string.
 * @param apiCredentials Object that contains app ID and Key to access Edamam API.
 */
class EdamamPageURL(
    searchFilterPreset: SearchFilterPresetModel,
    apiCredentials: EdamamCredentialsDB,
    fieldSet: FieldSet,
    inputText: String = ""
) {
    val value: String = build(searchFilterPreset, apiCredentials, fieldSet, inputText)

    private fun rangeFieldToRemoteQuery(tag: String, minValue: Float?, maxValue: Float?): String {
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

    private fun build(
        searchFilterPreset: SearchFilterPresetModel,
        apiCredentials: EdamamCredentialsDB,
        fieldSet: FieldSet,
        inputText: String,
    ): String {
        return """
        ${apiCredentials.recipe}
        ${fieldSet.fields}
        ${inputTextToRemoteQuery(inputText)}
        ${
            searchFilterPreset.basics.joinToString(separator = "") { field ->
                "&${rangeFieldToRemoteQuery(field.tag, field.minValue, field.maxValue)}"
            }
        }
        ${
            searchFilterPreset.nutrients.joinToString(separator = "") { field ->
                "&nutrients${rangeFieldToRemoteQuery(field.tag, field.minValue, field.maxValue)}"
            }
        }
        ${
            searchFilterPreset.categories.flatMap { category ->
                category.labels.map { category.tag to it.tag.replace(" ", "%20") }
            }.joinToString(separator = "") { (category, label) ->
                "&$category=$label"
            }
        }
        """.trimMultiline()
    }
}