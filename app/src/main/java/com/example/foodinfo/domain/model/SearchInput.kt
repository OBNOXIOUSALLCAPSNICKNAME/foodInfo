package com.example.foodinfo.domain.model


// TODO choose best dateTime format and lib
data class SearchInput(
    val ID: Int = 0,
    val inputText: String,
    val date: String = System.currentTimeMillis().toString()
)