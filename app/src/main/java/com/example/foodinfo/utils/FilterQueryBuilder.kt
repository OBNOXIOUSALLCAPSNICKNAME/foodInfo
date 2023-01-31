package com.example.foodinfo.utils

import android.util.Log
import com.example.foodinfo.local.dto.LabelOfRecipeDB
import com.example.foodinfo.local.dto.LabelRecipeAttrDB
import com.example.foodinfo.local.dto.NutrientOfRecipeDB
import com.example.foodinfo.local.dto.RecipeDB
import com.example.foodinfo.local.room.entity.RecipeEntity
import com.example.foodinfo.local.room.entity.SearchFilterEntity
import com.example.foodinfo.repository.model.filter_field.BasicOfFilterPreset
import com.example.foodinfo.repository.model.filter_field.CategoryOfFilterPreset
import com.example.foodinfo.repository.model.filter_field.NutrientOfFilterPreset


/**
 * Class that generate query by [SearchFilterEntity]
 *
 * Fields order:
 * * Basics - fastest one, search goes through the fields of [RecipeEntity] itself
 * * Nutrients - slowly than Range fields but only one query to another table
 * for multiple nutrients
 * * Labels - the slowest one, has more than one query to other tables
 *
 * Because of AND separator between subqueries if any of subquery don't
 * match the condition, row will be denied, so it makes sense to check the fastest
 * conditions first
 *
 * @sample queryExample
 */
data class FilterQueryBuilder(
    val basicsOfFilterPresets: List<BasicOfFilterPreset> = listOf(),
    val categoriesOfFilterPreset: List<CategoryOfFilterPreset> = listOf(),
    val nutrientsOfFilterPreset: List<NutrientOfFilterPreset> = listOf()
) {
    private var inputText: String = ""


    private fun nutrientFieldsToQuery(): String {
        if (nutrientsOfFilterPreset.isEmpty()) return ""
        val nutrientQueryList = nutrientsOfFilterPreset.map { field ->
            nutrientFieldToQuery(
                field.infoID,
                field.minValue,
                field.maxValue
            )
        }
        var query = "${RecipeDB.Columns.ID} IN "
        query += "(SELECT ${NutrientOfRecipeDB.Columns.RECIPE_ID} "
        query += "FROM ${NutrientOfRecipeDB.TABLE_NAME} WHERE CASE "
        query += nutrientQueryList.joinToString(" ")
        query += " ELSE NULL END "
        query += "GROUP BY ${NutrientOfRecipeDB.Columns.RECIPE_ID} "
        query += "HAVING  count(${NutrientOfRecipeDB.Columns.RECIPE_ID}) = ${nutrientQueryList.size})"
        return query
    }

    private fun nutrientFieldToQuery(
        infoID: Int, minValue: Float?, maxValue: Float?
    ): String {
        var query = ""
        query += "WHEN ${NutrientOfRecipeDB.Columns.INFO_ID} = '$infoID' THEN "
        query += rangeFieldToQuery(
            NutrientOfRecipeDB.Columns.TOTAL_VALUE,
            minValue,
            maxValue
        )
        return query
    }

    private fun rangeFieldToQuery(
        column: String, minValue: Any?, maxValue: Any?
    ): String {
        return if (minValue == null) {
            "$column <= $maxValue"
        } else if (maxValue == null) {
            "$column >= $minValue"
        } else {
            when (minValue == maxValue) {
                true  -> "$column = $maxValue"
                false -> "$column BETWEEN $minValue AND $maxValue"
            }
        }
    }

    private fun categoryFieldsToQuery(): String {
        val labels = categoriesOfFilterPreset.flatMap { it.labelInfoIDs }
        if (labels.isEmpty()) return ""

        var query = "${RecipeDB.Columns.ID} IN "
        query += "(SELECT ${LabelOfRecipeDB.Columns.RECIPE_ID} FROM "
        query += "(SELECT ${LabelOfRecipeDB.Columns.RECIPE_ID}, ${LabelRecipeAttrDB.Columns.CATEGORY_ID} "
        query += "FROM ${LabelOfRecipeDB.TABLE_NAME} INNER JOIN ${LabelRecipeAttrDB.TABLE_NAME} "
        query += "ON ${LabelOfRecipeDB.Columns.INFO_ID} = "
        query += "${LabelRecipeAttrDB.TABLE_NAME}.${LabelRecipeAttrDB.Columns.ID} "
        query += "WHERE ${LabelOfRecipeDB.Columns.INFO_ID} IN (${labels.joinToString(", ")}) "
        query += "GROUP BY ${LabelOfRecipeDB.Columns.RECIPE_ID}, ${LabelRecipeAttrDB.Columns.CATEGORY_ID}) "
        query += "GROUP BY ${LabelOfRecipeDB.Columns.RECIPE_ID} "
        query += "HAVING count(${LabelOfRecipeDB.Columns.RECIPE_ID}) = ${categoriesOfFilterPreset.size})"
        return query
    }


    private fun inputTextToQuery(): String {
        if (inputText == "") return ""
        return "${RecipeDB.Columns.NAME} LIKE '%$inputText%'"
    }

    fun setInputText(text: String) {
        inputText = text
    }

    fun getQuery(): String {
        var query = ""
        val subQueryList = arrayListOf<String>()
        subQueryList.addAll(basicsOfFilterPresets.map { field ->
            rangeFieldToQuery(
                field.columnName,
                field.minValue,
                field.maxValue
            )
        })
        subQueryList.add(nutrientFieldsToQuery())
        subQueryList.add(categoryFieldsToQuery())
        subQueryList.add(inputTextToQuery())
        subQueryList.removeAll(setOf(""))
        query += "SELECT * FROM ${RecipeDB.TABLE_NAME}"
        if (subQueryList.size > 0) {
            query += " WHERE " + subQueryList.joinToString(SEPARATOR)
        }
        Log.d("123", query)
        return query
    }


    companion object {
        private const val SEPARATOR = " AND "
    }
}