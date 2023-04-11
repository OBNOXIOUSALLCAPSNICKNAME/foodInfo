package com.example.foodinfo.local.room.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.foodinfo.local.model.SearchFilterDB


@Entity(
    tableName = SearchFilterDB.TABLE_NAME,
    indices = [Index(value = arrayOf(SearchFilterDB.Columns.NAME), unique = true)]
)
data class SearchFilterEntity(
    @PrimaryKey
    @ColumnInfo(name = Columns.NAME)
    override val name: String = DEFAULT_NAME

) : SearchFilterDB(
    name = name
)