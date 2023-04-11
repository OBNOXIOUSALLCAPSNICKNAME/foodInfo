package com.example.foodinfo.local.room.model.pojo

import androidx.room.ColumnInfo
import androidx.room.Relation
import com.example.foodinfo.local.model.BasicOfSearchFilterDB
import com.example.foodinfo.local.model.BasicOfSearchFilterExtendedDB
import com.example.foodinfo.local.model.BasicRecipeAttrDB
import com.example.foodinfo.local.room.model.entity.BasicRecipeAttrEntity


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