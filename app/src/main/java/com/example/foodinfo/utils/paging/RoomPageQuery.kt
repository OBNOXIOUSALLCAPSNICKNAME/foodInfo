package com.example.foodinfo.utils.paging

import com.example.foodinfo.local.dto.LabelOfRecipeDB
import com.example.foodinfo.local.dto.LabelRecipeAttrDB
import com.example.foodinfo.local.dto.NutrientOfRecipeDB
import com.example.foodinfo.local.dto.RecipeDB
import com.example.foodinfo.repository.model.CategoryOfFilterPresetModel
import com.example.foodinfo.repository.model.NutrientOfFilterPresetModel
import com.example.foodinfo.repository.model.SearchFilterPresetModel
import com.example.foodinfo.utils.extensions.trimMultiline


/**
 * Generates query to room DB.
 *
 * Because of **AND** separator between subqueries if any of subquery don't match the condition,
 * row will be denied, so it makes sense to check the fastest conditions first:
 *
 * * **Basics** - fastest one, search goes through the fields of [RecipeDB] itself.
 * * **Nutrients** - slowly than Range fields due to contains subquery to another table.
 * * **Labels** - the slowest one, in addition to the subquery also contains a grouping.
 *
 * Recipes from different queries may intersect by some of their properties (e.g. recipe with **time <= 90**
 * may also be with **dietType** = "**low-fat**"). Without internet, it's possible to just load everything
 * from local DB, (because the local DB will not change over time during the current session).
 * But with internet, recipes in remote page **N** may be different from recipes in local page **N**
 * For example, remote page **№5** will contain recipes **`[80..101]`**, but local DB page **№5** will
 * contain recipe **`[45, 68, .. 131]`** etc. (because they was previously cached from another queries).
 * This will cause the recipes to "jump" inside RecyclerView. To avoid that, set **isOffline = true** if
 * local DB updates expected.
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
 * ORDER BY last_update ASC
 * ~~~
 * @param searchFilterPreset Filter preset object that will be used to build query.
 * @param inputText If specified, result will contain only those recipes which name contain provided string.
 * @param isOnline If true, the result will not contain recipes that were added/updated before the session
 * started and sorted by [RecipeDB.lastUpdate] **ASC** to prevent list "jumps" while scrolling. Otherwise will
 * return all recipes matching the query ordered by [RecipeDB.lastUpdate] **DESC** to show most actual cached
 * recipes.
 */
class RoomPageQuery(
    searchFilterPreset: SearchFilterPresetModel,
    inputText: String = "",
    isOnline: Boolean
) {
    val value: String = build(searchFilterPreset, inputText, isOnline)

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

    private fun setLastUpdated(isOnline: Boolean): String {
        return if (isOnline) {
            "${RecipeDB.Columns.LAST_UPDATE} >= ${System.currentTimeMillis()}"
        } else {
            ""
        }
    }

    private fun setOrder(isOnline: Boolean): String {
        return if (isOnline) {
            "ORDER BY ${RecipeDB.Columns.LAST_UPDATE} ASC"
        } else {
            "ORDER BY ${RecipeDB.Columns.LAST_UPDATE} DESC"
        }
    }

    private fun build(
        searchFilterPreset: SearchFilterPresetModel,
        inputText: String,
        isOnline: Boolean
    ): String {
        val subQueryList = arrayListOf<String>()
        subQueryList.add(inputTextToLocalQuery(inputText))
        subQueryList.add(setLastUpdated(isOnline))
        subQueryList.addAll(searchFilterPreset.basics.map { field ->
            rangeFieldToLocalQuery(field.columnName, field.minValue, field.maxValue)
        })
        subQueryList.add(nutrientsToLocalQuery(searchFilterPreset.nutrients))
        subQueryList.add(labelsToLocalQuery(searchFilterPreset.categories))
        subQueryList.removeAll(setOf(""))

        return if (subQueryList.size == 0) {
            "SELECT * FROM ${
                RecipeDB.TABLE_NAME
            } ${
                setOrder(isOnline)
            }"
        } else {
            "SELECT * FROM ${
                RecipeDB.TABLE_NAME
            } WHERE ${
                subQueryList.joinToString(" AND ")
            } ${
                setOrder(isOnline)
            }"
        }
    }
}