package com.example.foodinfo.utils

import com.example.foodinfo.local.dto.*
import com.example.foodinfo.remote.dto.RecipeNetwork
import com.example.foodinfo.repository.model.CategoryOfFilterPresetModel
import com.example.foodinfo.repository.model.NutrientOfFilterPresetModel
import com.example.foodinfo.repository.model.SearchFilterPresetModel


enum class FieldSet(val fields: String) {
    FULL(""),
    BASIC(
        """
        &field=${RecipeNetwork.Fields.URI}
        &field=${RecipeNetwork.Fields.URL}
        &field=${RecipeNetwork.Fields.SOURCE}
        &field=${RecipeNetwork.Fields.LABEL}
        &field=${RecipeNetwork.Fields.IMAGE}
        &field=${RecipeNetwork.Fields.SERVINGS}
        &field=${RecipeNetwork.Fields.INGREDIENT_LINES}
        &field=${RecipeNetwork.Fields.CALORIES}
        &field=${RecipeNetwork.Fields.WEIGHT}
        &field=${RecipeNetwork.Fields.TIME}
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
        &field=${RecipeNetwork.Fields.INGREDIENT_LINES}
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


class EdamamRecipeURL(
    recipeID: String,
    fieldSet: FieldSet,
    apiCredentials: EdamamCredentialsDB
) {
    val value: String = "$recipeID${apiCredentials.recipe}${fieldSet.fields}"
}

/**
 * Remote query is simply API credentials and then just each filter piece appended with
 * **&** separator.
 * - All special symbols will be correctly translated (e.g. space will be replaced with **'%20'**
 * and **'+'** will be replaced with **%2B**).
 * - Result will contain only [RecipeDB] fields without labels and nutrients.
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
 * &field=ingredientLines
 * &field=calories
 * &field=totalWeight
 * &field=totalTime
 * ~~~
 */
class EdamamPageURL(
    searchFilterPreset: SearchFilterPresetModel,
    apiCredentials: EdamamCredentialsDB,
    inputText: String = ""
) {
    val value: String = build(searchFilterPreset, inputText, apiCredentials)

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
        inputText: String,
        apiCredentials: EdamamCredentialsDB
    ): String {
        return """
        ${apiCredentials.recipe}
        ${FieldSet.BASIC}
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

/**
 * Because of **AND** separator between subqueries if any of subquery don't match the condition,
 * row will be denied, so it makes sense to check the fastest conditions first:
 *
 * * **Basics** - fastest one, search goes through the fields of [RecipeDB] itself.
 * * **Nutrients** - slowly than Range fields due to contains subquery to another table.
 * * **Labels** - the slowest one, in addition to the subquery also contains a grouping.
 *
 * ###Example:
 * ~~~
 * SELECT * FROM recipe WHERE
 * name LIKE '%beef%'
 * AND servings BETWEEN 4.0 AND 15.0
 * AND ingredients <= 13.0
 * AND time >= 255.0
 * AND id IN (SELECT recipe_id FROM nutrient_of_recipe
 *     WHERE CASE
 *         WHEN info_id = 2 THEN total_value BETWEEN 6.0 AND 26.0
 *         WHEN info_id = 7 THEN total_value <= 26.0
 *         WHEN info_id = 12 THEN total_value >= 82.0
 *         ELSE NULL END
 *     GROUP BY recipe_id
 *     HAVING  count(recipe_id) = 3)
 * AND id IN (SELECT recipe_id FROM (
 *     SELECT recipe_id, category_id FROM label_of_recipe
 *     INNER JOIN label_recipe_attr ON info_id = label_recipe_attr.id
 *         WHERE info_id IN (1, 4, 8, 9, 46, 49, 52, 53)
 *         GROUP BY recipe_id, category_id)
 *     GROUP BY recipe_id HAVING count(recipe_id) = 3)
 * ~~~
 */
class RoomPageQuery(
    searchFilterPreset: SearchFilterPresetModel,
    inputText: String = "",
    isOffline: Boolean
) {
    val value: String = build(searchFilterPreset, inputText, isOffline)

    private fun labelsToLocalQuery(categories: List<CategoryOfFilterPresetModel>): String {
        val labels = categories.flatMap { it.labels }.map { it.infoID }
        return if (labels.isEmpty()) {
            ""
        } else {
            """
            ${RecipeDB.Columns.ID} IN 
            (SELECT ${LabelOfRecipeDB.Columns.RECIPE_ID} FROM 
            (SELECT ${LabelOfRecipeDB.Columns.RECIPE_ID}, ${LabelRecipeAttrDB.Columns.CATEGORY_ID} 
            FROM ${LabelOfRecipeDB.TABLE_NAME} INNER JOIN ${LabelRecipeAttrDB.TABLE_NAME} 
            ON ${LabelOfRecipeDB.Columns.INFO_ID} = ${LabelRecipeAttrDB.TABLE_NAME}.${LabelRecipeAttrDB.Columns.ID} 
            WHERE ${LabelOfRecipeDB.Columns.INFO_ID} IN (${labels.joinToString(", ")}) 
            GROUP BY ${LabelOfRecipeDB.Columns.RECIPE_ID}, ${LabelRecipeAttrDB.Columns.CATEGORY_ID}) 
            GROUP BY ${LabelOfRecipeDB.Columns.RECIPE_ID} 
            HAVING count(${LabelOfRecipeDB.Columns.RECIPE_ID}) = ${categories.size})
            """.trimMultiline()
        }
    }

    private fun nutrientsToLocalQuery(nutrients: List<NutrientOfFilterPresetModel>): String {
        return if (nutrients.isEmpty()) {
            ""
        } else {
            val nutrientQueryList = nutrients.map { field ->
                "WHEN ${NutrientOfRecipeDB.Columns.INFO_ID} = ${field.infoID} THEN ${
                    rangeFieldToLocalQuery(NutrientOfRecipeDB.Columns.VALUE, field.minValue, field.maxValue)
                }"
            }
            """
            ${RecipeDB.Columns.ID} IN 
            (SELECT ${NutrientOfRecipeDB.Columns.RECIPE_ID} 
            FROM ${NutrientOfRecipeDB.TABLE_NAME} WHERE CASE 
            ${nutrientQueryList.joinToString(" ")} 
            ELSE NULL END 
            GROUP BY ${NutrientOfRecipeDB.Columns.RECIPE_ID} 
            HAVING count(${NutrientOfRecipeDB.Columns.RECIPE_ID}) = ${nutrientQueryList.size})
            """.trimMultiline()
        }
    }

    private fun rangeFieldToLocalQuery(column: String, minValue: Float?, maxValue: Float?): String {
        return when {
            minValue == null -> {
                "$column <= $maxValue"
            }
            maxValue == null -> {
                "$column >= $minValue"
            }
            else             -> {
                "$column BETWEEN $minValue AND $maxValue"
            }
        }
    }

    private fun inputTextToLocalQuery(inputText: String): String {
        return if (inputText == "") {
            ""
        } else {
            "${RecipeDB.Columns.NAME} LIKE '%$inputText%'"
        }
    }

    private fun setLastUpdated(isOffline: Boolean): String {
        return if (isOffline) {
            ""
        } else {
            "${RecipeDB.Columns.LAST_UPDATE} = ${System.currentTimeMillis()}"
        }
    }

    private fun build(
        searchFilterPreset: SearchFilterPresetModel,
        inputText: String,
        isOffline: Boolean
    ): String {
        val subQueryList = arrayListOf<String>()
        subQueryList.add(inputTextToLocalQuery(inputText))
        subQueryList.add(setLastUpdated(isOffline))
        subQueryList.addAll(searchFilterPreset.basics.map { field ->
            rangeFieldToLocalQuery(field.columnName, field.minValue, field.maxValue)
        })
        subQueryList.add(nutrientsToLocalQuery(searchFilterPreset.nutrients))
        subQueryList.add(labelsToLocalQuery(searchFilterPreset.categories))
        subQueryList.removeAll(setOf(""))

        return if (subQueryList.size == 0) {
            "SELECT * FROM ${RecipeDB.TABLE_NAME}"
        } else {
            "SELECT * FROM ${RecipeDB.TABLE_NAME} WHERE ${subQueryList.joinToString(" AND ")}"
        }
    }
}

/**
 * Class that generate [RoomPageQuery] and [EdamamPageURL] query by [SearchFilterPresetModel]
 */
class RecipePageQuery(
    searchFilterPreset: SearchFilterPresetModel,
    apiCredentials: EdamamCredentialsDB,
    inputText: String,
    isOffline: Boolean
) {
    val local = RoomPageQuery(searchFilterPreset, inputText, isOffline)
    val remote = EdamamPageURL(searchFilterPreset, apiCredentials, inputText)

    override fun equals(other: Any?): Boolean {
        return (other is RecipePageQuery) &&
        this.local.value == other.local.value &&
        this.remote.value == other.remote.value
    }

    override fun hashCode(): Int {
        var result = local.hashCode()
        result = 31 * result + remote.hashCode()
        return result
    }
}
