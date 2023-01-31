package com.example.foodinfo.local.room.pojo

import androidx.room.ColumnInfo
import androidx.room.Relation
import com.example.foodinfo.local.dto.LabelOfRecipeDB
import com.example.foodinfo.local.dto.LabelOfSearchFilterDB
import com.example.foodinfo.local.dto.LabelOfSearchFilterExtendedDB
import com.example.foodinfo.local.dto.LabelRecipeAttrDB
import com.example.foodinfo.local.room.entity.LabelRecipeAttrEntity


data class LabelOfSearchFilterExtendedPOJO(
    @ColumnInfo(name = LabelOfSearchFilterDB.Columns.ID)
    override val ID: Int = 0,

    @ColumnInfo(name = LabelOfSearchFilterDB.Columns.FILTER_NAME)
    override val filterName: String,

    @ColumnInfo(name = LabelOfSearchFilterDB.Columns.INFO_ID)
    override val infoID: Int,

    @ColumnInfo(name = LabelOfSearchFilterDB.Columns.IS_SELECTED)
    override val isSelected: Boolean,

    @Relation(
        parentColumn = LabelOfRecipeDB.Columns.INFO_ID,
        entityColumn = LabelRecipeAttrDB.Columns.ID,
        entity = LabelRecipeAttrEntity::class
    )
    override val attrInfo: LabelRecipeAttrExtendedPOJO

) : LabelOfSearchFilterExtendedDB(
    ID = ID,
    filterName = filterName,
    infoID = infoID,
    isSelected = isSelected,
    attrInfo = attrInfo
)