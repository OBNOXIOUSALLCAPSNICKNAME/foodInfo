package com.example.foodinfo.data.mapper

import com.example.foodinfo.data.local.model.SearchInputDB
import com.example.foodinfo.domain.model.SearchInput


fun SearchInputDB.toModel(): SearchInput {
    return SearchInput(
        ID = this.ID,
        inputText = this.inputText,
        date = this.date.toString() // TODO implement proper conversion
    )
}

fun SearchInput.toDB(): SearchInputDB {
    return SearchInputDB(
        ID = this.ID,
        inputText = this.inputText,
        date = this.date.toLong() // TODO implement proper conversion
    )
}