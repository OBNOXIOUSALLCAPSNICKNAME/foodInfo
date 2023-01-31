package com.example.foodinfo.local.dao

import com.example.foodinfo.local.dto.SearchInputDB


// All UPDATE functions must truly UPDATE content (not DELETE and INSERT) so all indices will stay the same
interface SearchHistoryDAO {

    // select top 7 rows filtered by "date" column
    fun getHistoryLatest(inputText: String): List<SearchInputDB>

    fun getHistoryAll(): List<SearchInputDB>

    fun addHistory(searchInput: List<SearchInputDB>)

    fun addInput(searchInput: SearchInputDB)
}