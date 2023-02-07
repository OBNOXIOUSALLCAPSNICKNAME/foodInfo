package com.example.foodinfo.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodinfo.local.dao.SearchHistoryDAO
import com.example.foodinfo.local.dto.SearchInputDB
import com.example.foodinfo.local.room.entity.SearchInputEntity


@Dao
abstract class SearchHistoryDAORoom : SearchHistoryDAO {
    @Query(
        "SELECT * FROM ${SearchInputDB.TABLE_NAME} " +
                "WHERE ${SearchInputDB.Columns.INPUT_TEXT} " +
                "LIKE '%' || :inputText || '%' " +
                "ORDER BY ${SearchInputDB.Columns.DATE} DESC " +
                "LIMIT ${SearchInputDB.LIMIT}"
    )
    abstract fun getHistoryLatestEntity(inputText: String): List<SearchInputEntity>

    override fun getHistoryLatest(inputText: String): List<SearchInputDB> {
        return getHistoryLatestEntity(inputText)
    }

    @Query(
        "SELECT * FROM ${SearchInputDB.TABLE_NAME} " +
                "ORDER BY ${SearchInputDB.Columns.DATE}"
    )
    abstract fun getHistoryAllEntity(): List<SearchInputEntity>

    override fun getHistoryAll(): List<SearchInputDB> {
        return getHistoryAllEntity()
    }


    override fun addHistory(searchInput: List<SearchInputDB>) {
        addInputEntity(searchInput.map { SearchInputEntity.toEntity(it) })
    }

    override fun addInput(searchInput: SearchInputDB) {
        addInputEntity(SearchInputEntity.toEntity(searchInput))
    }


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addInputEntity(searchInput: SearchInputEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addInputEntity(searchInput: List<SearchInputEntity>)
}