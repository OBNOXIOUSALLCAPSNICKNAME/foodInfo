package com.example.foodinfo.domain.mapper

import com.example.foodinfo.local.model.SearchFilterDB
import com.example.foodinfo.local.model.SearchFilterExtendedDB
import com.example.foodinfo.domain.model.SearchFilterEditModel
import com.example.foodinfo.domain.model.SearchFilterModel
import com.example.foodinfo.domain.model.SearchFilterPresetModel


fun SearchFilterDB.toModel(): SearchFilterModel {
    return SearchFilterModel(name = this.name)
}

fun SearchFilterModel.toDB(): SearchFilterDB {
    return SearchFilterDB(name = this.name)
}

fun SearchFilterExtendedDB.toModelEdit(): SearchFilterEditModel {
    return SearchFilterEditModel(
        name = this.name,
        basics = this.basics.toModelEdit(),
        categories = this.labels.toModelPreview(),
        nutrients = this.nutrients.toModelPreview()
    )
}

fun SearchFilterExtendedDB.toModelPreset(): SearchFilterPresetModel {
    return SearchFilterPresetModel(
        basics = this.basics.toModelPreset(),
        nutrients = this.nutrients.toModelPreset(),
        categories = this.labels.toModelPreset()
    )
}