package com.example.foodinfo.data.local.room.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.foodinfo.data.local.model.LabelOfRecipeMetadataDB


@Entity(
    tableName = LabelOfRecipeMetadataDB.TABLE_NAME,
    indices = [Index(value = arrayOf(LabelOfRecipeMetadataDB.Columns.ID), unique = true)]
)
data class LabelOfRecipeMetadataEntity(
    @PrimaryKey
    @ColumnInfo(name = Columns.ID)
    override val ID: Int,

    @ColumnInfo(name = Columns.CATEGORY_ID)
    override val categoryID: Int,

    @ColumnInfo(name = Columns.TAG)
    override val tag: String,

    @ColumnInfo(name = Columns.NAME)
    override val name: String,

    @ColumnInfo(name = Columns.DESCRIPTION)
    override val description: String,

    @ColumnInfo(name = Columns.PREVIEW_URL)
    override val previewURL: String

) : LabelOfRecipeMetadataDB(
    ID = ID,
    categoryID = categoryID,
    tag = tag,
    name = name,
    description = description,
    previewURL = previewURL
) {
    companion object {
        operator fun invoke(item: LabelOfRecipeMetadataDB): LabelOfRecipeMetadataEntity {
            return LabelOfRecipeMetadataEntity(
                ID = item.ID,
                categoryID = item.categoryID,
                tag = item.tag,
                name = item.name,
                description = item.description,
                previewURL = item.previewURL
            )
        }
    }
}