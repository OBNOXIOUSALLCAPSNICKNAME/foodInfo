package com.example.foodinfo.repository.mapper

import com.example.foodinfo.local.dto.*
import com.example.foodinfo.remote.dto.NutrientOfRecipeNetwork
import com.example.foodinfo.remote.dto.NutrientRecipeAttrNetwork
import com.example.foodinfo.repository.model.*
import kotlin.math.max
import kotlin.math.min


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
        name = this.attrInfo!!.name,
        measure = this.attrInfo!!.measure,
        totalWeight = this.value,
        dailyWeight = this.attrInfo!!.dailyAllowance,
        dailyPercent = (this.value * 100 / this.attrInfo!!.dailyAllowance).toInt()
    )
}

fun NutrientOfSearchFilterExtendedDB.toDB(): NutrientOfSearchFilterDB {
    return NutrientOfSearchFilterDB(
        ID = this.ID,
        filterName = this.filterName,
        infoID = this.infoID,
        minValue = this.minValue,
        maxValue = this.maxValue
    )
}

fun NutrientOfSearchFilterExtendedDB.toDBLatest(): NutrientOfSearchFilterDB {
    return NutrientOfSearchFilterDB(
        ID = this.ID,
        filterName = this.filterName,
        infoID = this.infoID,
        minValue = if (this.minValue != null) max(
            this.minValue!!,
            this.attrInfo!!.rangeMin
        ) else null,
        maxValue = if (this.maxValue != null) min(
            this.maxValue!!,
            this.attrInfo!!.rangeMax
        ) else null
    )
}

fun List<NutrientOfSearchFilterExtendedDB>.toModelEdit(): List<NutrientOfSearchFilterEditModel> {
    return this.map { nutrient ->
        NutrientOfSearchFilterEditModel(
            ID = nutrient.ID,
            infoID = nutrient.attrInfo!!.ID,
            name = nutrient.attrInfo!!.name,
            stepSize = nutrient.attrInfo!!.stepSize,
            measure = nutrient.attrInfo!!.measure,
            rangeMin = nutrient.attrInfo!!.rangeMin,
            rangeMax = nutrient.attrInfo!!.rangeMax,
            minValue = nutrient.minValue,
            maxValue = nutrient.maxValue
        )
    }
}

fun List<NutrientOfSearchFilterExtendedDB>.toModelPreview(): List<NutrientOfSearchFilterPreviewModel> {
    return this
        .filterNot { it.minValue == null && it.maxValue == null }
        .map { nutrient ->
            NutrientOfSearchFilterPreviewModel(
                ID = nutrient.ID,
                name = nutrient.attrInfo!!.name,
                measure = nutrient.attrInfo!!.measure,
                minValue = nutrient.minValue,
                maxValue = nutrient.maxValue,
            )
        }
}

fun List<NutrientOfSearchFilterExtendedDB>.toModelPreset(): List<NutrientOfFilterPresetModel> {
    return this
        .filterNot { it.minValue == null && it.maxValue == null }
        .map { nutrient ->
            NutrientOfFilterPresetModel(
                tag = nutrient.attrInfo!!.tag,
                infoID = nutrient.attrInfo!!.ID,
                minValue = if (nutrient.minValue != null) max(
                    nutrient.minValue!!,
                    nutrient.attrInfo!!.rangeMin
                ) else null,
                maxValue = if (nutrient.maxValue != null) min(
                    nutrient.maxValue!!,
                    nutrient.attrInfo!!.rangeMax
                ) else null
            )
        }
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

fun NutrientOfSearchFilterExtendedDB.toDefault(): NutrientOfSearchFilterDB {
    return NutrientOfSearchFilterDB(
        ID = this.ID,
        filterName = this.filterName,
        infoID = this.infoID,
        minValue = null,
        maxValue = null
    )
}

fun NutrientRecipeAttrDB.toFilter(filterName: String): NutrientOfSearchFilterDB {
    return NutrientOfSearchFilterDB(
        filterName = filterName,
        infoID = this.ID,
        minValue = null,
        maxValue = null
    )
}


fun Map<String, NutrientOfRecipeNetwork>.toDB(
    recipeID: String,
    attrs: List<NutrientRecipeAttrDB>
): List<NutrientOfRecipeDB> {
    return this.map { (tag, nutrient) ->
        NutrientOfRecipeDB(
            recipeID = recipeID,
            infoID = attrs.first { it.tag.lowercase() == tag.lowercase() }.ID,
            value = nutrient.quantity
        )
    }
}

fun Map<String, NutrientOfRecipeNetwork>.toDBExtended(
    recipeID: String,
    attrs: List<NutrientRecipeAttrDB>
): List<NutrientOfRecipeExtendedDB> {
    return this.map { (tag, nutrient) ->
        NutrientOfRecipeExtendedDB(
            recipeID = recipeID,
            infoID = attrs.first { it.tag.lowercase() == tag.lowercase() }.ID,
            value = nutrient.quantity,
            attrInfo = null
        )
    }
}

fun NutrientRecipeAttrNetwork.toDB(): NutrientRecipeAttrDB {
    return NutrientRecipeAttrDB(
        ID = this.ID,
        tag = this.tag,
        name = this.name,
        measure = this.measure,
        description = this.description,
        hasRDI = this.hasRDI,
        previewURL = this.previewURL,
        dailyAllowance = this.dailyAllowance,
        stepSize = this.stepSize,
        rangeMin = this.rangeMin,
        rangeMax = this.rangeMax
    )
}