package com.example.foodinfo.local.room.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.foodinfo.local.model.SearchInputDB


@Entity(
    tableName = SearchInputDB.TABLE_NAME,
    indices = [Index(value = arrayOf(SearchInputDB.Columns.INPUT_TEXT), unique = true)]
)
data class SearchInputEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Columns.ID)
    override val ID: Int = 0,

    @ColumnInfo(name = Columns.INPUT_TEXT)
    override val inputText: String,

    @ColumnInfo(name = Columns.DATE)
    override val date: Long = System.currentTimeMillis()

) : SearchInputDB(
    ID = ID,
    inputText = inputText,
    date = date
) {
    companion object {
        operator fun invoke(item: SearchInputDB): SearchInputEntity {
            return SearchInputEntity(
                ID = item.ID,
                inputText = item.inputText,
                date = item.date
            )
        }
    }
}