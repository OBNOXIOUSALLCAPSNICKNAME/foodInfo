package com.example.foodinfo.local.dao

import com.example.foodinfo.local.dto.SearchInputDB


interface SearchHistoryDAO {

    // select top 7 rows filtered by "date" column
    fun getHistoryLatest(inputText: String): List<@JvmWildcard SearchInputDB>

    fun getHistoryAll(): List<@JvmWildcard SearchInputDB>

    // add functions must update content if it already in DB
    fun addHistory(searchInput: List<SearchInputDB>)

    fun addInput(searchInput: SearchInputDB)
}