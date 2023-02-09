package com.example.foodinfo.repository.mapper

import com.example.foodinfo.local.dto.BasicOfSearchFilterDB
import com.example.foodinfo.local.dto.BasicOfSearchFilterExtendedDB
import com.example.foodinfo.local.dto.BasicRecipeAttrDB
import com.example.foodinfo.remote.dto.BasicRecipeAttrNetwork
import com.example.foodinfo.repository.model.BasicOfSearchFilterEditModel
import com.example.foodinfo.repository.model.filter_field.BasicOfFilterPreset
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

fun BasicOfSearchFilterExtendedDB.toDefault(): BasicOfSearchFilterDB {
    return BasicOfSearchFilterDB(
        ID = this.ID,
        filterName = this.filterName,
        infoID = this.infoID,
        minValue = this.attrInfo!!.rangeMin,
        maxValue = this.attrInfo!!.rangeMax
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
        minValue = max(basic.minValue, this.rangeMin),
        maxValue = min(basic.maxValue, this.rangeMax)
    )
}

fun BasicRecipeAttrNetwork.toDB(): BasicRecipeAttrDB {
    throw java.lang.NullPointerException() //TODO implement conversion
}