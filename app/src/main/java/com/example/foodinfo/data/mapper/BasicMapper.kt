package com.example.foodinfo.data.mapper

import com.example.foodinfo.data.local.model.BasicOfSearchFilterDB
import com.example.foodinfo.data.local.model.BasicOfSearchFilterExtendedDB
import com.example.foodinfo.data.local.model.BasicRecipeAttrDB
import com.example.foodinfo.data.remote.model.BasicRecipeAttrNetwork
import com.example.foodinfo.domain.model.BasicRecipeAttr
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
        minValue = this.minValue?.let { max(it, this.attrInfo!!.rangeMin) },
        maxValue = this.maxValue?.let { min(it, this.attrInfo!!.rangeMax) }
    )
}

fun List<BasicOfSearchFilterExtendedDB>.toModel(): List<BasicOfSearchFilter> {
    return this.map { basic ->
        BasicOfSearchFilter(
            ID = basic.ID,
            infoID = basic.attrInfo!!.ID,
            tag = basic.attrInfo!!.tag!!,
            name = basic.attrInfo!!.name,
            columnName = basic.attrInfo!!.columnName,
            measure = basic.attrInfo!!.measure,
            stepSize = basic.attrInfo!!.stepSize,
            rangeMin = basic.attrInfo!!.rangeMin,
            rangeMax = basic.attrInfo!!.rangeMax,
            minValue = basic.minValue,
            maxValue = basic.maxValue
        )
    }
}

fun List<BasicOfSearchFilterExtendedDB>.toModelPreset(): List<BasicOfSearchFilterPreset> {
    return this.filterNot { it.minValue == null && it.maxValue == null }.map { basic ->
        BasicOfSearchFilterPreset(
            infoID = basic.infoID,
            tag = basic.attrInfo!!.tag!!,
            columnName = basic.attrInfo!!.columnName,
            minValue = basic.minValue,
            maxValue = basic.maxValue
        )
    }
}

fun BasicRecipeAttr.toFilter(filterName: String): BasicOfSearchFilterDB {
    return BasicOfSearchFilterDB(
        filterName = filterName,
        infoID = this.ID,
        minValue = null,
        maxValue = null
    )
}

fun BasicRecipeAttrDB.toModel(): BasicRecipeAttr {
    return BasicRecipeAttr(
        ID = this.ID,
        tag = this.tag,
        name = this.name,
        columnName = this.columnName,
        measure = this.measure,
        rangeMin = this.rangeMin,
        rangeMax = this.rangeMax,
        stepSize = this.stepSize
    )
}

fun BasicRecipeAttrNetwork.toDB(): BasicRecipeAttrDB {
    return BasicRecipeAttrDB(
        ID = this.ID,
        tag = this.tag,
        name = this.name,
        columnName = this.columnName,
        measure = this.measure,
        rangeMin = this.rangeMin,
        rangeMax = this.rangeMax,
        stepSize = this.stepSize
    )
}