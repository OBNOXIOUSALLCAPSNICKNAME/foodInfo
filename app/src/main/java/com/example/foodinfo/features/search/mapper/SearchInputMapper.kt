package com.example.foodinfo.features.search.mapper

import com.example.foodinfo.domain.model.SearchInput
import com.example.foodinfo.features.search.model.SearchInputVHModel


fun SearchInput.toVHModel(): SearchInputVHModel {
    return SearchInputVHModel(
        ID = this.ID,
        date = this.date,
        inputText = this.inputText
    )
}