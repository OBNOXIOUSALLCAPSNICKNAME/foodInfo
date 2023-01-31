package com.example.foodinfo.local.room.pojo

import androidx.room.ColumnInfo
import androidx.room.Relation
import com.example.foodinfo.local.dto.NutrientOfSearchFilterDB
import com.example.foodinfo.local.dto.NutrientOfSearchFilterExtendedDB
import com.example.foodinfo.local.dto.NutrientRecipeAttrDB
import com.example.foodinfo.local.room.entity.NutrientRecipeAttrEntity


data class NutrientOfSearchFilterExtendedPOJO(
    @ColumnInfo(name = NutrientOfSearchFilterDB.Columns.ID)
    override val ID: Int = 0,

    @ColumnInfo(name = NutrientOfSearchFilterDB.Columns.FILTER_NAME)
    override val filterName: String,

    @ColumnInfo(name = NutrientOfSearchFilterDB.Columns.INFO_ID)
    override val infoID: Int,

    @ColumnInfo(name = NutrientOfSearchFilterDB.Columns.MIN_VALUE)
    override val minValue: Float,

    @ColumnInfo(name = NutrientOfSearchFilterDB.Columns.MAX_VALUE)
    override val maxValue: Float,

    @Relation(
        parentColumn = NutrientOfSearchFilterDB.Columns.INFO_ID,
        entityColumn = NutrientRecipeAttrDB.Columns.ID
    )
    override val attrInfo: NutrientRecipeAttrEntity

) : NutrientOfSearchFilterExtendedDB(
    ID = ID,
    filterName = filterName,
    infoID = infoID,
    minValue = minValue,
    maxValue = maxValue,
    attrInfo = attrInfo
)
