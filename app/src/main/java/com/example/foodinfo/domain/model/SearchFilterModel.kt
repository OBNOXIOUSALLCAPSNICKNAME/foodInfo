package com.example.foodinfo.domain.model

import com.example.foodinfo.local.model.SearchFilterDB


data class SearchFilterModel(
    val name: String = SearchFilterDB.DEFAULT_NAME
)