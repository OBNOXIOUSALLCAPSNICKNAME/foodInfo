package com.example.foodinfo.local.room.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.foodinfo.local.model.CategoryRecipeAttrDB


@Entity(
    tableName = CategoryRecipeAttrDB.TABLE_NAME,
    indices = [Index(value = arrayOf(CategoryRecipeAttrDB.Columns.ID), unique = true)]
)
data class CategoryRecipeAttrEntity(
    @PrimaryKey
    @ColumnInfo(name = Columns.ID)
    override val ID: Int,

    @ColumnInfo(name = Columns.TAG)
    override val tag: String,

    @ColumnInfo(name = Columns.NAME)
    override val name: String,

    @ColumnInfo(name = Columns.PREVIEW_URL)
    override val previewURL: String

) : CategoryRecipeAttrDB(
    ID = ID,
    tag = tag,
    name = name,
    previewURL = previewURL
) {
    companion object {
        operator fun invoke(item: CategoryRecipeAttrDB): CategoryRecipeAttrEntity {
            return CategoryRecipeAttrEntity(
                ID = item.ID,
                tag = item.tag,
                name = item.name,
                previewURL = item.previewURL
            )
        }
    }
}