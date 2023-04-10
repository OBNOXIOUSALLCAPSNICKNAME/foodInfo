package com.example.foodinfo.repository.model


// TODO choose best dateTime format and lib
data class SearchInputModel(
    val ID: Int = 0,
    val inputText: String,
    val date: String = System.currentTimeMillis().toString()
)