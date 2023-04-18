package com.example.foodinfo.data.mapper

import com.example.foodinfo.data.local.model.*
import com.example.foodinfo.data.remote.model.LabelRecipeAttrNetwork
import com.example.foodinfo.domain.model.LabelHint
import com.example.foodinfo.domain.model.LabelOfRecipe
import com.example.foodinfo.domain.model.LabelRecipeAttr
import com.example.foodinfo.domain.model.LabelOfSearchFilter
import com.example.foodinfo.domain.model.LabelOfSearchFilterPreset
import com.example.foodinfo.utils.glide.svg.SVGModel


fun LabelRecipeAttrDB.toModelHint(): LabelHint {
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

fun LabelOfSearchFilterExtendedDB.toModel(): LabelOfSearchFilter {
    return LabelOfSearchFilter(
        ID = this.ID,
        infoID = this.infoID,
        tag = this.attrInfo!!.tag,
        name = this.attrInfo!!.name,
        isSelected = this.isSelected
    )
}

fun LabelOfSearchFilterExtendedDB.toModelPreset(): LabelOfSearchFilterPreset {
    return LabelOfSearchFilterPreset(
        infoID = this.infoID,
        tag = this.attrInfo!!.tag
    )
}

fun LabelRecipeAttr.toFilter(filterName: String): LabelOfSearchFilterDB {
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
    attrs: List<LabelRecipeAttr>
): List<LabelOfRecipeDB> {
    val attrsMap = attrs.associate { it.name.lowercase() to it.ID }
    return this.map { label ->
        LabelOfRecipeDB(
            recipeID = recipeID,
            infoID = attrsMap[label.lowercase()]!!
        )
    }
}

fun LabelRecipeAttrDB.toModel(): LabelRecipeAttr {
    return LabelRecipeAttr(
        ID = this.ID,
        categoryID = this.categoryID,
        tag = this.tag,
        name = this.name,
        description = this.description,
        previewURL = this.previewURL
    )
}