package com.example.foodinfo.local.room.pojo

import androidx.room.ColumnInfo
import androidx.room.Relation
import com.example.foodinfo.local.dto.CategoryRecipeAttrDB
import com.example.foodinfo.local.dto.LabelRecipeAttrDB
import com.example.foodinfo.local.dto.LabelRecipeAttrExtendedDB
import com.example.foodinfo.local.room.entity.CategoryRecipeAttrEntity


data class LabelRecipeAttrExtendedPOJO(
    @ColumnInfo(name = LabelRecipeAttrDB.Columns.ID)
    override val ID: Int,

    @ColumnInfo(name = LabelRecipeAttrDB.Columns.CATEGORY_ID)
    override val categoryID: Int,

    @ColumnInfo(name = LabelRecipeAttrDB.Columns.TAG)
    override val tag: String,

    @ColumnInfo(name = LabelRecipeAttrDB.Columns.NAME)
    override val name: String,

    @ColumnInfo(name = LabelRecipeAttrDB.Columns.DESCRIPTION)
    override val description: String,

    @ColumnInfo(name = LabelRecipeAttrDB.Columns.PREVIEW_URL)
    override val previewURL: String,

    @Relation(
        parentColumn = LabelRecipeAttrDB.Columns.CATEGORY_ID,
        entityColumn = CategoryRecipeAttrDB.Columns.ID,
    )
    override val categoryInfo: CategoryRecipeAttrEntity?

) : LabelRecipeAttrExtendedDB(
    ID = ID,
    categoryID = categoryID,
    tag = tag,
    name = name,
    description = description,
    previewURL = previewURL,
    categoryInfo = categoryInfo
)