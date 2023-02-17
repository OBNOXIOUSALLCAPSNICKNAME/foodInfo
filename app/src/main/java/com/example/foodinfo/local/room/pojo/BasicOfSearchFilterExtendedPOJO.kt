package com.example.foodinfo.local.room.pojo

import androidx.room.ColumnInfo
import androidx.room.Relation
import com.example.foodinfo.local.dto.BasicOfSearchFilterDB
import com.example.foodinfo.local.dto.BasicOfSearchFilterExtendedDB
import com.example.foodinfo.local.dto.BasicRecipeAttrDB
import com.example.foodinfo.local.room.entity.BasicRecipeAttrEntity


data class BasicOfSearchFilterExtendedPOJO(
    @ColumnInfo(name = BasicOfSearchFilterDB.Columns.ID)
    override val ID: Int = 0,

    @ColumnInfo(name = BasicOfSearchFilterDB.Columns.INFO_ID)
    override val infoID: Int,

    @ColumnInfo(name = BasicOfSearchFilterDB.Columns.FILTER_NAME)
    override val filterName: String,

    @ColumnInfo(name = BasicOfSearchFilterDB.Columns.MIN_VALUE)
    override val minValue: Float?,

    @ColumnInfo(name = BasicOfSearchFilterDB.Columns.MAX_VALUE)
    override val maxValue: Float?,

    @Relation(
        parentColumn = BasicOfSearchFilterDB.Columns.INFO_ID,
        entityColumn = BasicRecipeAttrDB.Columns.ID
    )
    override val attrInfo: BasicRecipeAttrEntity?

) : BasicOfSearchFilterExtendedDB(
    ID = ID,
    infoID = infoID,
    filterName = filterName,
    minValue = minValue,
    maxValue = maxValue,
    attrInfo = attrInfo
)