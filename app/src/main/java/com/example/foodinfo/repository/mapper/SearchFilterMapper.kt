package com.example.foodinfo.repository.mapper

import com.example.foodinfo.local.dto.SearchFilterDB
import com.example.foodinfo.local.dto.SearchFilterExtendedDB
import com.example.foodinfo.repository.model.SearchFilterEditModel
import com.example.foodinfo.repository.model.SearchFilterModel


fun SearchFilterDB.toModel(): SearchFilterModel {
    return SearchFilterModel(name = this.name)
}

fun SearchFilterModel.toDB(): SearchFilterDB {
    return SearchFilterDB(name = this.name)
}

fun SearchFilterExtendedDB.toModelEdit(): SearchFilterEditModel {
    return SearchFilterEditModel(
        name = this.name,
        baseFields = this.basic.map { it.toModelEdit() },
        categories = this.labels.toModelFilterPreview(),
        nutrients = this.nutrients.toModelPreview()
    )
}