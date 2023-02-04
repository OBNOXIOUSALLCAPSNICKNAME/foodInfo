package com.example.foodinfo.repository.mapper

import com.example.foodinfo.local.dto.CategoryRecipeAttrDB
import com.example.foodinfo.local.dto.LabelOfRecipeExtendedDB
import com.example.foodinfo.local.dto.LabelOfSearchFilterExtendedDB
import com.example.foodinfo.remote.dto.CategoryRecipeAttrNetwork
import com.example.foodinfo.repository.model.*
import com.example.foodinfo.repository.model.filter_field.CategoryOfFilterPreset


fun CategoryRecipeAttrDB.toModel(): CategorySearchModel {
    return CategorySearchModel(
        ID = this.ID,
        name = this.name,
        preview = SVGModel(this.previewURL)
    )
}

fun List<LabelOfRecipeExtendedDB>.toModelRecipe(): List<CategoryOfRecipeModel> {
    return this.groupBy { label -> label.attrInfo.categoryInfo.name }.entries.map { category ->
        CategoryOfRecipeModel(
            name = category.key,
            labels = category.value.map { it.toModelShort() }
        )
    }
}

fun List<LabelOfSearchFilterExtendedDB>.toModelFilterEdit(categoryID: Int): CategoryOfSearchFilterEditModel {
    return CategoryOfSearchFilterEditModel(
        labels = this.filter { it.attrInfo.categoryID == categoryID }.map { it.toModelEdit() }
    )
}

fun List<LabelOfSearchFilterExtendedDB>.toModelFilterPreview(): List<CategoryOfSearchFilterPreviewModel> {
    return this.groupBy { label -> label.attrInfo.categoryID }.entries.map { category ->
        CategoryOfSearchFilterPreviewModel(
            ID = category.key,
            name = category.value.first().attrInfo.categoryInfo.name,
            labels = category.value.filter { it.isSelected }.map { it.toModelShort() }
        )
    }
}

fun List<LabelOfSearchFilterExtendedDB>.toModelFilterField(): List<CategoryOfFilterPreset> {
    return this.groupBy { label -> label.attrInfo.categoryInfo.name }.entries.map { category ->
        CategoryOfFilterPreset(
            labelInfoIDs = category.value.filter { it.isSelected }.map { it.infoID }
        )
    }.filter { it.labelInfoIDs.isNotEmpty() }
}

fun CategoryRecipeAttrNetwork.toDB(): CategoryRecipeAttrDB {
    throw java.lang.NullPointerException() //TODO implement conversion
}