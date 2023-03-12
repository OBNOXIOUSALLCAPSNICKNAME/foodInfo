package com.example.foodinfo.repository.mapper

import com.example.foodinfo.local.dto.CategoryRecipeAttrDB
import com.example.foodinfo.local.dto.LabelOfRecipeExtendedDB
import com.example.foodinfo.local.dto.LabelOfSearchFilterExtendedDB
import com.example.foodinfo.local.dto.LabelRecipeAttrExtendedDB
import com.example.foodinfo.remote.dto.CategoryRecipeAttrNetwork
import com.example.foodinfo.repository.model.*


fun CategoryRecipeAttrDB.toModel(): CategorySearchModel {
    return CategorySearchModel(
        ID = this.ID,
        name = this.name,
        preview = SVGModel(this.previewURL)
    )
}

fun List<LabelRecipeAttrExtendedDB>.toModelSearch(): CategoryTargetSearchModel {
    return CategoryTargetSearchModel(
        name = this.first().categoryInfo!!.name,
        labels = this.map { it.toModelSearch() }
    )
}

fun List<LabelOfRecipeExtendedDB>.toModelRecipe(): List<CategoryOfRecipeModel> {
    return this.groupBy { label -> label.attrInfo!!.categoryInfo!!.name }.entries.map { category ->
        CategoryOfRecipeModel(
            name = category.key,
            labels = category.value.map { it.toModelShort() }
        )
    }
}

fun List<LabelOfSearchFilterExtendedDB>.toModelEdit(categoryID: Int): CategoryOfSearchFilterEditModel {
    val labels = this.filter { it.attrInfo!!.categoryID == categoryID }
    return CategoryOfSearchFilterEditModel(
        name = labels.first().attrInfo!!.categoryInfo!!.name,
        labels = labels.map { it.toModelEdit() }
    )
}

fun List<LabelOfSearchFilterExtendedDB>.toModelPreview(): List<CategoryOfSearchFilterPreviewModel> {
    return this.groupBy { label -> label.attrInfo!!.categoryID }.entries.map { category ->
        CategoryOfSearchFilterPreviewModel(
            ID = category.key,
            name = category.value.first().attrInfo!!.categoryInfo!!.name,
            labels = category.value.filter { it.isSelected }.map { it.toModelShort() }
        )
    }
}

fun List<LabelOfSearchFilterExtendedDB>.toModelPreset(): List<CategoryOfFilterPresetModel> {
    return this.groupBy { label -> label.attrInfo!!.categoryInfo!!.name }.entries.map { category ->
        CategoryOfFilterPresetModel(
            tag = category.value.first().attrInfo!!.categoryInfo!!.tag,
            labels = category.value.filter { it.isSelected }.map { label ->
                LabelOfFilterPresetModel(label.attrInfo!!.tag, label.infoID)
            }
        )
    }.filter { it.labels.isNotEmpty() }
}

fun CategoryRecipeAttrNetwork.toDB(): CategoryRecipeAttrDB {
    throw java.lang.NullPointerException() //TODO implement conversion
}