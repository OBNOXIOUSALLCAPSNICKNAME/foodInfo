package com.example.foodinfo.local.dao

import com.example.foodinfo.local.dto.SearchInputDB


interface SearchHistoryDAO {

    // select top 7 rows filtered by "date" column
    fun getHistoryLatest(inputText: String): List<@JvmSuppressWildcards(suppress = false) SearchInputDB>

    fun getHistoryAll(): List<@JvmSuppressWildcards(suppress = false) SearchInputDB>

    fun addHistory(searchInput: List<SearchInputDB>)

    fun addInput(searchInput: SearchInputDB)
}