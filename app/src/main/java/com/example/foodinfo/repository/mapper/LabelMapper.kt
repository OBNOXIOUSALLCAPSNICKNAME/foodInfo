package com.example.foodinfo.repository.mapper

import com.example.foodinfo.local.dto.*
import com.example.foodinfo.remote.dto.LabelRecipeAttrNetwork
import com.example.foodinfo.repository.model.*


fun LabelRecipeAttrDB.toModelHint(): LabelHintModel {
    return LabelHintModel(
        ID = this.ID,
        name = this.name,
        description = this.description,
        preview = SVGModel(this.previewURL)
    )
}

fun LabelRecipeAttrExtendedDB.toModelSearch(): LabelSearchModel {
    return LabelSearchModel(
        ID = this.ID,
        name = this.name,
        preview = SVGModel(this.previewURL)
    )
}

fun LabelOfRecipeExtendedDB.toModelShort(): LabelShortModel {
    return LabelShortModel(
        infoID = this.infoID,
        name = this.attrInfo!!.name
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

fun LabelOfSearchFilterExtendedDB.toModelEdit(): LabelOfSearchFilterEditModel {
    return LabelOfSearchFilterEditModel(
        ID = this.ID,
        infoID = this.infoID,
        name = this.attrInfo!!.name,
        isSelected = this.isSelected
    )
}

fun LabelOfSearchFilterExtendedDB.toModelShort(): LabelShortModel {
    return LabelShortModel(
        infoID = this.infoID,
        name = this.attrInfo!!.name
    )
}

fun LabelOfSearchFilterExtendedDB.toModelPreset(): LabelOfFilterPresetModel {
    return LabelOfFilterPresetModel(
        tag = this.attrInfo!!.tag,
        infoID = this.infoID
    )
}


fun LabelOfSearchFilterEditModel.toDB(filterName: String): LabelOfSearchFilterDB {
    return LabelOfSearchFilterDB(
        ID = this.ID,
        filterName = filterName,
        infoID = this.infoID,
        isSelected = this.isSelected
    )
}

fun LabelOfSearchFilterExtendedDB.toDefault(): LabelOfSearchFilterDB {
    return LabelOfSearchFilterDB(
        ID = this.ID,
        filterName = this.filterName,
        infoID = this.infoID,
        isSelected = false
    )
}

fun LabelRecipeAttrDB.toFilter(filterName: String): LabelOfSearchFilterDB {
    return LabelOfSearchFilterDB(
        filterName = filterName,
        infoID = this.ID,
        isSelected = false
    )
}


fun LabelRecipeAttrNetwork.toDB(): LabelRecipeAttrDB {
    return LabelRecipeAttrDB(
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
    attrs: List<LabelRecipeAttrDB>
): List<LabelOfRecipeDB> {
    val attrsMap = attrs.associate { it.name.lowercase() to it.ID }
    return this.map { label ->
        LabelOfRecipeDB(
            recipeID = recipeID,
            infoID = attrsMap[label.lowercase()]!!
        )
    }
}