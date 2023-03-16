package com.example.foodinfo.repository.mapper

import com.example.foodinfo.local.dto.SearchInputDB
import com.example.foodinfo.repository.model.SearchInputModel


fun SearchInputDB.toModel(): SearchInputModel {
    return SearchInputModel(
        ID = this.ID,
        inputText = this.inputText,
        date = this.date.toString() // TODO implement proper conversion
    )
}

fun SearchInputModel.toDB(): SearchInputDB {
    return SearchInputDB(
        ID = this.ID,
        inputText = this.inputText,
        date = this.date.toLong() // TODO implement proper conversion
    )
}