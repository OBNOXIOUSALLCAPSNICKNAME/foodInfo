package com.example.foodinfo.data.mapper

import com.example.foodinfo.data.local.model.BasicOfRecipeMetadataDB
import com.example.foodinfo.data.local.model.BasicOfSearchFilterDB
import com.example.foodinfo.data.local.model.BasicOfSearchFilterExtendedDB
import com.example.foodinfo.data.remote.model.BasicOfRecipeMetadataNetwork
import com.example.foodinfo.domain.model.BasicOfRecipeMetadata
import com.example.foodinfo.domain.model.BasicOfSearchFilter
import com.example.foodinfo.domain.model.BasicOfSearchFilterPreset
import kotlin.math.max
import kotlin.math.min


fun BasicOfSearchFilterExtendedDB.toDB(): BasicOfSearchFilterDB {
    return BasicOfSearchFilterDB(
        ID = this.ID,
        filterName = this.filterName,
        infoID = this.infoID,
        minValue = this.minValue,
        maxValue = this.maxValue
    )
}

fun BasicOfSearchFilterExtendedDB.toDBLatest(): BasicOfSearchFilterDB {
    return BasicOfSearchFilterDB(
        ID = this.ID,
        filterName = this.filterName,
        infoID = this.infoID,
        minValue = this.minValue?.let { max(it, this.metadata!!.rangeMin) },
        maxValue = this.maxValue?.let { min(it, this.metadata!!.rangeMax) }
    )
}

fun List<BasicOfSearchFilterExtendedDB>.toModel(): List<BasicOfSearchFilter> {
    return this.map { basic ->
        BasicOfSearchFilter(
            ID = basic.ID,
            infoID = basic.metadata!!.ID,
            tag = basic.metadata!!.tag!!,
            name = basic.metadata!!.name,
            columnName = basic.metadata!!.columnName,
            measure = basic.metadata!!.measure,
            stepSize = basic.metadata!!.stepSize,
            rangeMin = basic.metadata!!.rangeMin,
            rangeMax = basic.metadata!!.rangeMax,
            minValue = basic.minValue,
            maxValue = basic.maxValue
        )
    }
}

fun List<BasicOfSearchFilterExtendedDB>.toModelPreset(): List<BasicOfSearchFilterPreset> {
    return this.filterNot { it.minValue == null && it.maxValue == null }.map { basic ->
        BasicOfSearchFilterPreset(
            infoID = basic.infoID,
            tag = basic.metadata!!.tag!!,
            columnName = basic.metadata!!.columnName,
            precision = basic.metadata!!.precision,
            minValue = basic.minValue,
            maxValue = basic.maxValue
        )
    }
}

fun BasicOfRecipeMetadata.toFilter(filterName: String): BasicOfSearchFilterDB {
    return BasicOfSearchFilterDB(
        filterName = filterName,
        infoID = this.ID,
        minValue = null,
        maxValue = null
    )
}

fun BasicOfRecipeMetadataDB.toModel(): BasicOfRecipeMetadata {
    return BasicOfRecipeMetadata(
        ID = this.ID,
        tag = this.tag,
        name = this.name,
        columnName = this.columnName,
        measure = this.measure,
        precision = this.precision,
        rangeMin = this.rangeMin,
        rangeMax = this.rangeMax,
        stepSize = this.stepSize
    )
}

fun BasicOfRecipeMetadataNetwork.toDB(): BasicOfRecipeMetadataDB {
    return BasicOfRecipeMetadataDB(
        ID = this.ID,
        tag = this.tag,
        name = this.name,
        columnName = this.columnName,
        measure = this.measure,
        precision = this.precision,
        rangeMin = this.rangeMin,
        rangeMax = this.rangeMax,
        stepSize = this.stepSize
    )
}