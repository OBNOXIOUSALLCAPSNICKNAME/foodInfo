package com.example.foodinfo.data.mapper

import com.example.foodinfo.data.local.model.*
import com.example.foodinfo.data.remote.model.NutrientOfRecipeMetadataNetwork
import com.example.foodinfo.data.remote.model.NutrientOfRecipeNetwork
import com.example.foodinfo.domain.model.*
import com.example.foodinfo.utils.extensions.toPercent
import com.example.foodinfo.utils.glide.svg.SVGModel
import kotlin.math.max
import kotlin.math.min


fun NutrientOfRecipeMetadataDB.toModelHint(): NutrientHint {
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
        name = this.metadata!!.name,
        measure = this.metadata!!.measure,
        totalWeight = this.value,
        dailyWeight = this.metadata!!.dailyAllowance,
        dailyPercent = this.value.toPercent(this.metadata!!.dailyAllowance)
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
        minValue = this.minValue?.let { max(it, this.metadata!!.rangeMin) },
        maxValue = this.maxValue?.let { min(it, this.metadata!!.rangeMax) }
    )
}

fun List<NutrientOfSearchFilterExtendedDB>.toModel(): List<NutrientOfSearchFilter> {
    return this.map { nutrient ->
        NutrientOfSearchFilter(
            ID = nutrient.ID,
            infoID = nutrient.metadata!!.ID,
            tag = nutrient.metadata!!.tag,
            name = nutrient.metadata!!.name,
            hasRDI = nutrient.metadata!!.hasRDI,
            dailyAllowance = nutrient.metadata!!.dailyAllowance,
            stepSize = nutrient.metadata!!.stepSize,
            measure = nutrient.metadata!!.measure,
            rangeMin = nutrient.metadata!!.rangeMin,
            rangeMax = nutrient.metadata!!.rangeMax,
            minValue = nutrient.minValue,
            maxValue = nutrient.maxValue
        )
    }
}

fun List<NutrientOfSearchFilterExtendedDB>.toModelPreset(): List<NutrientOfSearchFilterPreset> {
    return this.filterNot { it.minValue == null && it.maxValue == null }.map { nutrient ->
        NutrientOfSearchFilterPreset(
            infoID = nutrient.metadata!!.ID,
            tag = nutrient.metadata!!.tag,
            minValue = nutrient.minValue,
            maxValue = nutrient.maxValue
        )
    }
}

fun NutrientOfRecipeMetadata.toFilter(filterName: String): NutrientOfSearchFilterDB {
    return NutrientOfSearchFilterDB(
        filterName = filterName,
        infoID = this.ID,
        minValue = null,
        maxValue = null
    )
}


fun Map<String, NutrientOfRecipeNetwork>.toDB(
    recipeID: String,
    metadata: List<NutrientOfRecipeMetadata>
): List<NutrientOfRecipeDB> {
    val metadataMap = metadata.associate { it.tag.lowercase() to it.ID }
    return this.map { (tag, nutrient) ->
        NutrientOfRecipeDB(
            recipeID = recipeID,
            infoID = metadataMap[tag.lowercase()]!!,
            value = nutrient.value
        )
    }
}

fun NutrientOfRecipeMetadataNetwork.toDB(): NutrientOfRecipeMetadataDB {
    return NutrientOfRecipeMetadataDB(
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

fun NutrientOfRecipeMetadataDB.toModel(): NutrientOfRecipeMetadata {
    return NutrientOfRecipeMetadata(
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