package com.example.foodinfo.repository.mapper

import com.example.foodinfo.local.dto.BasicOfSearchFilterDB
import com.example.foodinfo.local.dto.BasicOfSearchFilterExtendedDB
import com.example.foodinfo.repository.model.BasicOfSearchFilterEditModel
import com.example.foodinfo.repository.model.filter_field.BasicOfFilterPreset


fun BasicOfSearchFilterExtendedDB.toModelEdit(): BasicOfSearchFilterEditModel {
    return BasicOfSearchFilterEditModel(
        ID = this.ID,
        infoUID = this.attrInfo.ID,
        name = this.attrInfo.name,
        measure = this.attrInfo.measure,
        stepSize = this.attrInfo.stepSize,
        rangeMin = this.attrInfo.rangeMin,
        rangeMax = this.attrInfo.rangeMax,
        minValue = this.minValue,
        maxValue = this.maxValue
    )
}

fun List<BasicOfSearchFilterExtendedDB>.toModelFilterField(): List<BasicOfFilterPreset> {
    return this.map { field ->
        BasicOfFilterPreset(
            columnName = field.attrInfo.columnName,
            minValue = if (field.minValue == field.attrInfo.rangeMin) null else field.minValue,
            maxValue = if (field.maxValue == field.attrInfo.rangeMax) null else field.maxValue
        )
    }.filter { !(it.minValue == null && it.maxValue == null) }
}


fun BasicOfSearchFilterEditModel.toDB(filterName: String): BasicOfSearchFilterDB {
    return BasicOfSearchFilterDB(
        ID = this.ID,
        infoID = this.infoUID,
        filterName = filterName,
        minValue = this.minValue,
        maxValue = this.maxValue
    )
}