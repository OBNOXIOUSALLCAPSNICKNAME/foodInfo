package com.example.foodinfo.domain.mapper

import com.example.foodinfo.domain.model.BasicOfFilterPresetModel
import com.example.foodinfo.domain.model.BasicOfSearchFilterEditModel
import com.example.foodinfo.local.model.BasicOfSearchFilterDB
import com.example.foodinfo.local.model.BasicOfSearchFilterExtendedDB
import com.example.foodinfo.local.model.BasicRecipeAttrDB
import com.example.foodinfo.remote.model.BasicRecipeAttrNetwork
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

fun List<BasicOfSearchFilterExtendedDB>.toModelEdit(): List<BasicOfSearchFilterEditModel> {
    return this.map { basic ->
        BasicOfSearchFilterEditModel(
            ID = basic.ID,
            infoID = basic.attrInfo!!.ID,
            name = basic.attrInfo!!.name,
            measure = basic.attrInfo!!.measure,
            stepSize = basic.attrInfo!!.stepSize,
            rangeMin = basic.attrInfo!!.rangeMin,
            rangeMax = basic.attrInfo!!.rangeMax,
            minValue = basic.minValue,
            maxValue = basic.maxValue
        )
    }
}

fun List<BasicOfSearchFilterExtendedDB>.toModelPreset(): List<BasicOfFilterPresetModel> {
    return this
        .filterNot { (it.minValue == null && it.maxValue == null) || it.attrInfo!!.tag == null }
        .map { basic ->
            BasicOfFilterPresetModel(
                tag = basic.attrInfo!!.tag!!,
                columnName = basic.attrInfo!!.columnName,
                minValue = basic.minValue?.let { max(it, basic.attrInfo!!.rangeMin) },
                maxValue = basic.maxValue?.let { min(it, basic.attrInfo!!.rangeMax) }
            )
        }
}

fun BasicOfSearchFilterEditModel.toDB(filterName: String): BasicOfSearchFilterDB {
    return BasicOfSearchFilterDB(
        ID = this.ID,
        infoID = this.infoID,
        filterName = filterName,
        minValue = this.minValue,
        maxValue = this.maxValue
    )
}

fun BasicRecipeAttrDB.toFilter(filterName: String): BasicOfSearchFilterDB {
    return BasicOfSearchFilterDB(
        filterName = filterName,
        infoID = this.ID,
        minValue = null,
        maxValue = null
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