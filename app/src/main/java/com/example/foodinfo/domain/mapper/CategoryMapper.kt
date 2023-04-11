package com.example.foodinfo.domain.mapper

import com.example.foodinfo.local.model.CategoryRecipeAttrDB
import com.example.foodinfo.local.model.LabelOfRecipeExtendedDB
import com.example.foodinfo.local.model.LabelOfSearchFilterExtendedDB
import com.example.foodinfo.local.model.LabelRecipeAttrExtendedDB
import com.example.foodinfo.remote.model.CategoryRecipeAttrNetwork
import com.example.foodinfo.domain.model.*
import com.example.foodinfo.utils.glide.svg.SVGModel


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
    return this.groupBy { it.attrInfo!!.categoryInfo!!.name }.values.map { labels ->
        CategoryOfRecipeModel(
            name = labels.first().attrInfo!!.categoryInfo!!.name,
            labels = labels.map { it.toModelShort() }
        )
    }
}

fun List<LabelOfSearchFilterExtendedDB>.toModelEdit(categoryID: Int): CategoryOfSearchFilterEditModel {
    this.filter { it.attrInfo!!.categoryID == categoryID }.also { labels ->
        return CategoryOfSearchFilterEditModel(
            name = labels.first().attrInfo!!.categoryInfo!!.name,
            labels = labels.map { it.toModelEdit() }
        )
    }
}

fun List<LabelOfSearchFilterExtendedDB>.toModelPreview(): List<CategoryOfSearchFilterPreviewModel> {
    return this.groupBy { it.attrInfo!!.categoryID }.values.map { labels ->
        CategoryOfSearchFilterPreviewModel(
            ID = labels.first().attrInfo!!.categoryInfo!!.ID,
            name = labels.first().attrInfo!!.categoryInfo!!.name,
            labels = labels.filter { it.isSelected }.map { it.toModelShort() }
        )
    }
}

fun List<LabelOfSearchFilterExtendedDB>.toModelPreset(): List<CategoryOfFilterPresetModel> {
    return this.filter { it.isSelected }.groupBy { it.attrInfo!!.categoryInfo!!.name }.values.map { labels ->
        CategoryOfFilterPresetModel(
            tag = labels.first().attrInfo!!.categoryInfo!!.tag,
            labels = labels.map { it.toModelPreset() }
        )
    }
}

fun CategoryRecipeAttrNetwork.toDB(): CategoryRecipeAttrDB {
    return CategoryRecipeAttrDB(
        ID = this.ID,
        tag = this.tag,
        name = this.name,
        previewURL = this.previewURL
    )
}