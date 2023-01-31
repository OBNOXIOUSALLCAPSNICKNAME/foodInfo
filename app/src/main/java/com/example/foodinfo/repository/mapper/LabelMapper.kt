package com.example.foodinfo.repository.mapper

import com.example.foodinfo.local.dto.LabelOfRecipeExtendedDB
import com.example.foodinfo.local.dto.LabelOfSearchFilterDB
import com.example.foodinfo.local.dto.LabelOfSearchFilterExtendedDB
import com.example.foodinfo.local.dto.LabelRecipeAttrDB
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