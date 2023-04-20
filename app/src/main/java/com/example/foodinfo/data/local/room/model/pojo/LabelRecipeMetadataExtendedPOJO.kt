package com.example.foodinfo.data.local.room.model.pojo

import androidx.room.ColumnInfo
import androidx.room.Relation
import com.example.foodinfo.data.local.model.CategoryOfRecipeMetadataDB
import com.example.foodinfo.data.local.model.LabelOfRecipeMetadataDB
import com.example.foodinfo.data.local.model.LabelRecipeMetadataExtendedDB
import com.example.foodinfo.data.local.room.model.entity.CategoryOfRecipeMetadataEntity


data class LabelRecipeMetadataExtendedPOJO(
    @ColumnInfo(name = LabelOfRecipeMetadataDB.Columns.ID)
    override val ID: Int,

    @ColumnInfo(name = LabelOfRecipeMetadataDB.Columns.CATEGORY_ID)
    override val categoryID: Int,

    @ColumnInfo(name = LabelOfRecipeMetadataDB.Columns.TAG)
    override val tag: String,

    @ColumnInfo(name = LabelOfRecipeMetadataDB.Columns.NAME)
    override val name: String,

    @ColumnInfo(name = LabelOfRecipeMetadataDB.Columns.DESCRIPTION)
    override val description: String,

    @ColumnInfo(name = LabelOfRecipeMetadataDB.Columns.PREVIEW_URL)
    override val previewURL: String,

    @Relation(
        parentColumn = LabelOfRecipeMetadataDB.Columns.CATEGORY_ID,
        entityColumn = CategoryOfRecipeMetadataDB.Columns.ID,
    )
    override val categoryMetadata: CategoryOfRecipeMetadataEntity?

) : LabelRecipeMetadataExtendedDB(
    ID = ID,
    categoryID = categoryID,
    tag = tag,
    name = name,
    description = description,
    previewURL = previewURL,
    categoryMetadata = categoryMetadata
)