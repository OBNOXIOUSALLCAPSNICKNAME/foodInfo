package com.example.foodinfo.repository.mapper

import com.example.foodinfo.local.dto.*
import com.example.foodinfo.remote.dto.LabelOfRecipeNetwork
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

fun LabelRecipeAttrDB.toModelSearch(): LabelSearchModel {
    return LabelSearchModel(
        ID = this.ID,
        name = this.name,
        preview = SVGModel(this.previewURL)
    )
}

fun LabelOfRecipeExtendedDB.toModelShort(): LabelShortModel {
    return LabelShortModel(
        infoID = this.infoID,
        name = this.attrInfo.name
    )
}

fun LabelOfSearchFilterExtendedDB.toModelEdit(): LabelOfSearchFilterEditModel {
    return LabelOfSearchFilterEditModel(
        ID = this.ID,
        infoID = this.infoID,
        name = this.attrInfo.name,
        isSelected = this.isSelected
    )
}

fun LabelOfSearchFilterExtendedDB.toModelShort(): LabelShortModel {
    return LabelShortModel(
        infoID = this.infoID,
        name = this.attrInfo.name
    )
}


fun LabelOfSearchFilterEditModel.toDB(filterName: String, category: String): LabelOfSearchFilterDB {
    return LabelOfSearchFilterDB(
        ID = this.ID,
        filterName = filterName,
        infoID = this.infoID,
        isSelected = this.isSelected
    )
}

fun LabelOfRecipeNetwork.toDB(): LabelOfRecipeDB {
    throw java.lang.NullPointerException() //TODO implement conversion
}

fun LabelRecipeAttrNetwork.toDB(): LabelRecipeAttrDB {
    throw java.lang.NullPointerException() //TODO implement conversion
}