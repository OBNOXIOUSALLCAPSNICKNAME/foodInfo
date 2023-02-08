package com.example.foodinfo.repository.mapper

import com.example.foodinfo.local.dto.SearchFilterDB
import com.example.foodinfo.local.dto.SearchFilterExtendedDB
import com.example.foodinfo.repository.model.SearchFilterEditModel
import com.example.foodinfo.repository.model.SearchFilterModel
import com.example.foodinfo.repository.model.SearchFilterPresetModel


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