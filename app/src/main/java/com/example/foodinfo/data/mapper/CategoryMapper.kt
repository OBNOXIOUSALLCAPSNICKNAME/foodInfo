package com.example.foodinfo.data.mapper

import com.example.foodinfo.data.local.model.CategoryOfRecipeMetadataDB
import com.example.foodinfo.data.local.model.LabelOfRecipeExtendedDB
import com.example.foodinfo.data.local.model.LabelOfSearchFilterExtendedDB
import com.example.foodinfo.data.remote.model.CategoryOfRecipeMetadataNetwork
import com.example.foodinfo.domain.model.CategoryOfRecipe
import com.example.foodinfo.domain.model.CategoryOfRecipeMetadata
import com.example.foodinfo.domain.model.CategoryOfSearchFilter
import com.example.foodinfo.domain.model.CategoryOfSearchFilterPreset


fun List<LabelOfRecipeExtendedDB>.toRecipeCategories(): List<CategoryOfRecipe> {
    return this.groupBy { it.metadata!!.categoryMetadata!!.name }.values.map { labels ->
        CategoryOfRecipe(
            name = labels.first().metadata!!.categoryMetadata!!.name,
            labels = labels.map { it.toModel() }
        )
    }
}

fun List<LabelOfSearchFilterExtendedDB>.toModel(categoryID: Int): CategoryOfSearchFilter {
    this.filter { it.metadata!!.categoryID == categoryID }.also { labels ->
        return CategoryOfSearchFilter(
            tag = labels.first().metadata!!.categoryMetadata!!.tag,
            name = labels.first().metadata!!.categoryMetadata!!.name,
            labels = labels.map(LabelOfSearchFilterExtendedDB::toModel)
        )
    }
}

fun List<LabelOfSearchFilterExtendedDB>.toModel(): List<CategoryOfSearchFilter> {
    return this.groupBy { it.metadata!!.categoryMetadata!!.name }.values.map { labels ->
        CategoryOfSearchFilter(
            tag = labels.first().metadata!!.categoryMetadata!!.tag,
            name = labels.first().metadata!!.categoryMetadata!!.name,
            labels = labels.map(LabelOfSearchFilterExtendedDB::toModel)
        )
    }
}

fun List<LabelOfSearchFilterExtendedDB>.toModelPreset(): List<CategoryOfSearchFilterPreset> {
    return this.filter { it.isSelected }.groupBy { it.metadata!!.categoryMetadata!!.name }.values.map { labels ->
        CategoryOfSearchFilterPreset(
            tag = labels.first().metadata!!.categoryMetadata!!.tag,
            labels = labels.map(LabelOfSearchFilterExtendedDB::toModelPreset)
        )
    }
}

fun CategoryOfRecipeMetadataNetwork.toDB(): CategoryOfRecipeMetadataDB {
    return CategoryOfRecipeMetadataDB(
        ID = this.ID,
        tag = this.tag,
        name = this.name,
        previewURL = this.previewURL
    )
}

fun CategoryOfRecipeMetadataDB.toModel(): CategoryOfRecipeMetadata {
    return CategoryOfRecipeMetadata(
        ID = this.ID,
        tag = this.tag,
        name = this.name,
        previewURL = this.previewURL
    )
}