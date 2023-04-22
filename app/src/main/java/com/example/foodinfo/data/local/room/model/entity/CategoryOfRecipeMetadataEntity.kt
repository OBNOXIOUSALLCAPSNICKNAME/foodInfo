package com.example.foodinfo.data.local.room.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.foodinfo.data.local.model.CategoryOfRecipeMetadataDB


@Entity(
    tableName = CategoryOfRecipeMetadataDB.TABLE_NAME,
    indices = [Index(value = arrayOf(CategoryOfRecipeMetadataDB.Columns.ID), unique = true)]
)
data class CategoryOfRecipeMetadataEntity(
    @PrimaryKey
    @ColumnInfo(name = Columns.ID)
    override val ID: Int,

    @ColumnInfo(name = Columns.TAG)
    override val tag: String,

    @ColumnInfo(name = Columns.NAME)
    override val name: String,

    @ColumnInfo(name = Columns.PREVIEW_URL)
    override val previewURL: String

) : CategoryOfRecipeMetadataDB(
    ID = ID,
    tag = tag,
    name = name,
    previewURL = previewURL
) {
    companion object {
        operator fun invoke(item: CategoryOfRecipeMetadataDB): CategoryOfRecipeMetadataEntity {
            return CategoryOfRecipeMetadataEntity(
                ID = item.ID,
                tag = item.tag,
                name = item.name,
                previewURL = item.previewURL
            )
        }
    }
}