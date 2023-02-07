package com.example.foodinfo.repository.mapper

import com.example.foodinfo.local.dto.BasicOfSearchFilterDB
import com.example.foodinfo.local.dto.BasicOfSearchFilterExtendedDB
import com.example.foodinfo.local.dto.BasicRecipeAttrDB
import com.example.foodinfo.remote.dto.BasicRecipeAttrNetwork
import com.example.foodinfo.repository.model.BasicOfSearchFilterEditModel
import com.example.foodinfo.repository.model.filter_field.BasicOfFilterPreset
import java.lang.Float
import kotlin.String


fun BasicOfSearchFilterExtendedDB.toDB(): BasicOfSearchFilterDB {
    return BasicOfSearchFilterDB(
        ID = this.ID,
        filterName = this.filterName,
        infoID = this.infoID,
        minValue = this.minValue,
        maxValue = this.maxValue
    )
}

fun BasicOfSearchFilterExtendedDB.toModelEdit(): BasicOfSearchFilterEditModel {
    return BasicOfSearchFilterEditModel(
        ID = this.ID,
        infoID = this.attrInfo!!.ID,
        name = this.attrInfo!!.name,
        measure = this.attrInfo!!.measure,
        stepSize = this.attrInfo!!.stepSize,
        rangeMin = this.attrInfo!!.rangeMin,
        rangeMax = this.attrInfo!!.rangeMax,
        minValue = this.minValue,
        maxValue = this.maxValue
    )
}

fun List<BasicOfSearchFilterExtendedDB>.toModelPreset(): List<BasicOfFilterPreset> {
    return this.map { field ->
        BasicOfFilterPreset(
            columnName = field.attrInfo!!.columnName,
            minValue = if (field.minValue == field.attrInfo!!.rangeMin) null else field.minValue,
            maxValue = if (field.maxValue == field.attrInfo!!.rangeMax) null else field.maxValue
        )
    }.filter { !(it.minValue == null && it.maxValue == null) }
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

fun BasicRecipeAttrDB.toFilterDefault(filterName: String): BasicOfSearchFilterDB {
    return BasicOfSearchFilterDB(
        filterName = filterName,
        infoID = this.ID,
        minValue = this.rangeMin,
        maxValue = this.rangeMax
    )
}

fun BasicRecipeAttrDB.toFilter(basic: BasicOfSearchFilterExtendedDB): BasicOfSearchFilterDB {
    return BasicOfSearchFilterDB(
        ID = basic.ID,
        filterName = basic.filterName,
        infoID = this.ID,
        minValue = Float.max(basic.minValue, this.rangeMin),
        maxValue = Float.min(basic.maxValue, this.rangeMax)
    )
}

fun BasicRecipeAttrNetwork.toDB(): BasicRecipeAttrDB {
    throw java.lang.NullPointerException() //TODO implement conversion
}