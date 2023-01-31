package com.example.foodinfo.local.room.pojo

import androidx.room.ColumnInfo
import androidx.room.Relation
import com.example.foodinfo.local.dto.LabelOfRecipeDB
import com.example.foodinfo.local.dto.LabelOfRecipeExtendedDB
import com.example.foodinfo.local.dto.LabelRecipeAttrDB
import com.example.foodinfo.local.room.entity.LabelRecipeAttrEntity


data class LabelOfRecipeExtendedPOJO(
    @ColumnInfo(name = LabelOfRecipeDB.Columns.ID)
    override val ID: Int = 0,

    @ColumnInfo(name = LabelOfRecipeDB.Columns.RECIPE_ID)
    override val recipeID: String,

    @ColumnInfo(name = LabelOfRecipeDB.Columns.INFO_ID)
    override val infoID: Int,

    @Relation(
        parentColumn = LabelOfRecipeDB.Columns.INFO_ID,
        entityColumn = LabelRecipeAttrDB.Columns.ID,
        entity = LabelRecipeAttrEntity::class
    )
    override val attrInfo: LabelRecipeAttrExtendedPOJO

) : LabelOfRecipeExtendedDB(
    ID = ID,
    recipeID = recipeID,
    infoID = infoID,
    attrInfo = attrInfo
)