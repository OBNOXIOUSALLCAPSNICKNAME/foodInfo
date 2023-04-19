package com.example.foodinfo.data.mapper

import com.example.foodinfo.data.local.model.CategoryRecipeAttrDB
import com.example.foodinfo.data.local.model.LabelOfRecipeExtendedDB
import com.example.foodinfo.data.local.model.LabelOfSearchFilterExtendedDB
import com.example.foodinfo.data.remote.model.CategoryRecipeAttrNetwork
import com.example.foodinfo.domain.model.CategoryOfRecipe
import com.example.foodinfo.domain.model.CategoryOfSearchFilter
import com.example.foodinfo.domain.model.CategoryOfSearchFilterPreset
import com.example.foodinfo.domain.model.CategoryRecipeAttr


fun List<LabelOfRecipeExtendedDB>.toRecipeCategories(): List<CategoryOfRecipe> {
    return this.groupBy { it.attrInfo!!.categoryInfo!!.name }.values.map { labels ->
        CategoryOfRecipe(
            name = labels.first().attrInfo!!.categoryInfo!!.name,
            labels = labels.map { it.toModel() }
        )
    }
}

fun List<LabelOfSearchFilterExtendedDB>.toModel(categoryID: Int): CategoryOfSearchFilter {
    this.filter { it.attrInfo!!.categoryID == categoryID }.also { labels ->
        return CategoryOfSearchFilter(
            tag = labels.first().attrInfo!!.categoryInfo!!.tag,
            name = labels.first().attrInfo!!.categoryInfo!!.name,
            labels = labels.map(LabelOfSearchFilterExtendedDB::toModel)
        )
    }
}

fun List<LabelOfSearchFilterExtendedDB>.toModel(): List<CategoryOfSearchFilter> {
    return this.groupBy { it.attrInfo!!.categoryInfo!!.name }.values.map { labels ->
        CategoryOfSearchFilter(
            tag = labels.first().attrInfo!!.categoryInfo!!.tag,
            name = labels.first().attrInfo!!.categoryInfo!!.name,
            labels = labels.map(LabelOfSearchFilterExtendedDB::toModel)
        )
    }
}

fun List<LabelOfSearchFilterExtendedDB>.toModelPreset(): List<CategoryOfSearchFilterPreset> {
    return this.filter { it.isSelected }.groupBy { it.attrInfo!!.categoryInfo!!.name }.values.map { labels ->
        CategoryOfSearchFilterPreset(
            tag = labels.first().attrInfo!!.categoryInfo!!.tag,
            labels = labels.map(LabelOfSearchFilterExtendedDB::toModelPreset)
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

fun CategoryRecipeAttrDB.toModel(): CategoryRecipeAttr {
    return CategoryRecipeAttr(
        ID = this.ID,
        tag = this.tag,
        name = this.name,
        previewURL = this.previewURL
    )
}