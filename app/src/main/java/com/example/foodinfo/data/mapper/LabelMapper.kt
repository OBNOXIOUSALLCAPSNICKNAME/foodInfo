package com.example.foodinfo.data.mapper

import com.example.foodinfo.data.local.model.*
import com.example.foodinfo.data.remote.model.LabelOfRecipeMetadataNetwork
import com.example.foodinfo.domain.model.*
import com.example.foodinfo.utils.glide.svg.SVGModel


fun LabelOfRecipeMetadataDB.toModelHint(): LabelHint {
    return LabelHint(
        ID = this.ID,
        name = this.name,
        description = this.description,
        preview = SVGModel(this.previewURL)
    )
}

fun LabelOfRecipeExtendedDB.toModel(): LabelOfRecipe {
    return LabelOfRecipe(
        ID = this.ID,
        infoID = this.infoID,
        name = this.metadata!!.name
    )
}

fun LabelOfSearchFilterExtendedDB.toDB(): LabelOfSearchFilterDB {
    return LabelOfSearchFilterDB(
        ID = this.ID,
        filterName = this.filterName,
        infoID = this.infoID,
        isSelected = this.isSelected
    )
}

fun LabelOfSearchFilterExtendedDB.toModel(): LabelOfSearchFilter {
    return LabelOfSearchFilter(
        ID = this.ID,
        infoID = this.infoID,
        tag = this.metadata!!.tag,
        name = this.metadata!!.name,
        isSelected = this.isSelected
    )
}

fun LabelOfSearchFilterExtendedDB.toModelPreset(): LabelOfSearchFilterPreset {
    return LabelOfSearchFilterPreset(
        infoID = this.infoID,
        tag = this.metadata!!.tag
    )
}

fun LabelOfRecipeMetadata.toFilter(filterName: String): LabelOfSearchFilterDB {
    return LabelOfSearchFilterDB(
        filterName = filterName,
        infoID = this.ID,
        isSelected = false
    )
}


fun LabelOfRecipeMetadataNetwork.toDB(): LabelOfRecipeMetadataDB {
    return LabelOfRecipeMetadataDB(
        ID = this.ID,
        categoryID = this.categoryID,
        tag = this.tag,
        name = this.name,
        description = this.description,
        previewURL = this.previewURL
    )
}

fun List<String>.toDB(
    recipeID: String,
    metadata: List<LabelOfRecipeMetadata>
): List<LabelOfRecipeDB> {
    val metadataMap = metadata.associate { it.name.lowercase() to it.ID }
    return this.map { label ->
        LabelOfRecipeDB(
            recipeID = recipeID,
            infoID = metadataMap[label.lowercase()]!!
        )
    }
}

fun LabelOfRecipeMetadataDB.toModel(): LabelOfRecipeMetadata {
    return LabelOfRecipeMetadata(
        ID = this.ID,
        categoryID = this.categoryID,
        tag = this.tag,
        name = this.name,
        description = this.description,
        previewURL = this.previewURL
    )
}