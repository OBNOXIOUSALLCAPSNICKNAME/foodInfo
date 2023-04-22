package com.example.foodinfo.data.local.room.model.pojo

import androidx.room.ColumnInfo
import androidx.room.Relation
import com.example.foodinfo.data.local.model.NutrientOfRecipeMetadataDB
import com.example.foodinfo.data.local.model.NutrientOfSearchFilterDB
import com.example.foodinfo.data.local.model.NutrientOfSearchFilterExtendedDB
import com.example.foodinfo.data.local.room.model.entity.NutrientOfRecipeMetadataEntity


data class NutrientOfSearchFilterExtendedPOJO(
    @ColumnInfo(name = NutrientOfSearchFilterDB.Columns.ID)
    override val ID: Int = 0,

    @ColumnInfo(name = NutrientOfSearchFilterDB.Columns.FILTER_NAME)
    override val filterName: String,

    @ColumnInfo(name = NutrientOfSearchFilterDB.Columns.INFO_ID)
    override val infoID: Int,

    @ColumnInfo(name = NutrientOfSearchFilterDB.Columns.MIN_VALUE)
    override val minValue: Float?,

    @ColumnInfo(name = NutrientOfSearchFilterDB.Columns.MAX_VALUE)
    override val maxValue: Float?,

    @Relation(
        parentColumn = NutrientOfSearchFilterDB.Columns.INFO_ID,
        entityColumn = NutrientOfRecipeMetadataDB.Columns.ID
    )
    override val metadata: NutrientOfRecipeMetadataEntity?

) : NutrientOfSearchFilterExtendedDB(
    ID = ID,
    filterName = filterName,
    infoID = infoID,
    minValue = minValue,
    maxValue = maxValue,
    metadata = metadata
)
