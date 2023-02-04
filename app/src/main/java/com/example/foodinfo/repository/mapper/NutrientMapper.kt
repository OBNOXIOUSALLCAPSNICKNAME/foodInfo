package com.example.foodinfo.repository.mapper

import com.example.foodinfo.local.dto.*
import com.example.foodinfo.remote.dto.NutrientOfRecipeNetwork
import com.example.foodinfo.remote.dto.NutrientRecipeAttrNetwork
import com.example.foodinfo.repository.model.*
import com.example.foodinfo.repository.model.filter_field.NutrientOfFilterPreset


fun NutrientRecipeAttrDB.toModelHint(): NutrientHintModel {
    return NutrientHintModel(
        ID = this.ID,
        label = this.name,
        description = this.description,
        preview = SVGModel(this.previewURL)
    )
}

fun NutrientOfRecipeExtendedDB.toModel(): NutrientOfRecipeModel {
    return NutrientOfRecipeModel(
        ID = this.ID,
        infoID = this.infoID,
        name = this.attrInfo.name,
        measure = this.attrInfo.measure,
        totalWeight = this.value,
        dailyWeight = this.attrInfo.dailyAllowance,
        dailyPercent = (this.value * 100 / this.attrInfo.dailyAllowance).toInt()
    )
}

fun NutrientOfSearchFilterExtendedDB.toModelEdit(): NutrientOfSearchFilterEditModel {
    return NutrientOfSearchFilterEditModel(
        ID = this.ID,
        infoID = this.attrInfo.ID,
        name = this.attrInfo.name,
        stepSize = this.attrInfo.stepSize,
        measure = this.attrInfo.measure,
        rangeMin = this.attrInfo.rangeMin,
        rangeMax = this.attrInfo.rangeMax,
        minValue = this.minValue,
        maxValue = this.maxValue
    )
}

fun List<NutrientOfSearchFilterExtendedDB>.toModelPreview(): List<NutrientFilterPreviewModel> {
    return this.mapNotNull { nutrient ->
        if (
            nutrient.minValue == nutrient.attrInfo.rangeMin &&
            nutrient.maxValue == nutrient.attrInfo.rangeMax
        ) {
            null
        } else {
            NutrientFilterPreviewModel(
                ID = nutrient.ID,
                name = nutrient.attrInfo.name,
                measure = nutrient.attrInfo.measure,
                minValue = nutrient.minValue,
                maxValue = nutrient.maxValue,
            )
        }
    }
}

fun List<NutrientOfSearchFilterExtendedDB>.toModelFilterField(): List<NutrientOfFilterPreset> {
    return this.map { nutrient ->
        NutrientOfFilterPreset(
            infoID = nutrient.attrInfo.ID,
            minValue = if (nutrient.minValue == nutrient.attrInfo.rangeMin) null else nutrient.minValue,
            maxValue = if (nutrient.maxValue == nutrient.attrInfo.rangeMax) null else nutrient.maxValue
        )
    }.filter { !(it.minValue == null && it.maxValue == null) }
}


fun NutrientOfSearchFilterEditModel.toDB(filterName: String): NutrientOfSearchFilterDB {
    return NutrientOfSearchFilterDB(
        ID = this.ID,
        infoID = this.ID,
        filterName = filterName,
        minValue = this.minValue,
        maxValue = this.maxValue
    )
}

fun NutrientOfRecipeNetwork.toDB(): NutrientOfRecipeDB {
    throw java.lang.NullPointerException() //TODO implement conversion
}

fun NutrientRecipeAttrNetwork.toDB(): NutrientRecipeAttrDB {
    throw java.lang.NullPointerException() //TODO implement conversion
}