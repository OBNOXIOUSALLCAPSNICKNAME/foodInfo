package com.example.foodinfo.data.mapper

import com.example.foodinfo.data.local.model.*
import com.example.foodinfo.data.remote.model.NutrientOfRecipeNetwork
import com.example.foodinfo.data.remote.model.NutrientRecipeAttrNetwork
import com.example.foodinfo.domain.model.*
import com.example.foodinfo.utils.extensions.toPercent
import com.example.foodinfo.utils.glide.svg.SVGModel
import kotlin.math.max
import kotlin.math.min


fun NutrientRecipeAttrDB.toModelHint(): NutrientHint {
    return NutrientHint(
        ID = this.ID,
        name = this.name,
        description = this.description,
        preview = SVGModel(this.previewURL)
    )
}

fun NutrientOfRecipeExtendedDB.toModel(): NutrientOfRecipe {
    return NutrientOfRecipe(
        ID = this.ID,
        infoID = this.infoID,
        name = this.attrInfo!!.name,
        measure = this.attrInfo!!.measure,
        totalWeight = this.value,
        dailyWeight = this.attrInfo!!.dailyAllowance,
        dailyPercent = this.value.toPercent(this.attrInfo!!.dailyAllowance)
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
        minValue = this.minValue?.let { max(it, this.attrInfo!!.rangeMin) },
        maxValue = this.maxValue?.let { min(it, this.attrInfo!!.rangeMax) }
    )
}

fun List<NutrientOfSearchFilterExtendedDB>.toModel(): List<NutrientOfSearchFilter> {
    return this.map { nutrient ->
        NutrientOfSearchFilter(
            ID = nutrient.ID,
            infoID = nutrient.attrInfo!!.ID,
            tag = nutrient.attrInfo!!.tag,
            name = nutrient.attrInfo!!.name,
            hasRDI = nutrient.attrInfo!!.hasRDI,
            dailyAllowance = nutrient.attrInfo!!.dailyAllowance,
            stepSize = nutrient.attrInfo!!.stepSize,
            measure = nutrient.attrInfo!!.measure,
            rangeMin = nutrient.attrInfo!!.rangeMin,
            rangeMax = nutrient.attrInfo!!.rangeMax,
            minValue = nutrient.minValue,
            maxValue = nutrient.maxValue
        )
    }
}

fun List<NutrientOfSearchFilterExtendedDB>.toModelPreset(): List<NutrientOfSearchFilterPreset> {
    return this.filterNot { it.minValue == null && it.maxValue == null }.map { nutrient ->
        NutrientOfSearchFilterPreset(
            infoID = nutrient.attrInfo!!.ID,
            tag = nutrient.attrInfo!!.tag,
            minValue = nutrient.minValue,
            maxValue = nutrient.maxValue
        )
    }
}

fun NutrientRecipeAttr.toFilter(filterName: String): NutrientOfSearchFilterDB {
    return NutrientOfSearchFilterDB(
        filterName = filterName,
        infoID = this.ID,
        minValue = null,
        maxValue = null
    )
}


fun Map<String, NutrientOfRecipeNetwork>.toDB(
    recipeID: String,
    attrs: List<NutrientRecipeAttr>
): List<NutrientOfRecipeDB> {
    val attrsMap = attrs.associate { it.tag.lowercase() to it.ID }
    return this.map { (tag, nutrient) ->
        NutrientOfRecipeDB(
            recipeID = recipeID,
            infoID = attrsMap[tag.lowercase()]!!,
            value = nutrient.value
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

fun NutrientRecipeAttrDB.toModel(): NutrientRecipeAttr {
    return NutrientRecipeAttr(
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