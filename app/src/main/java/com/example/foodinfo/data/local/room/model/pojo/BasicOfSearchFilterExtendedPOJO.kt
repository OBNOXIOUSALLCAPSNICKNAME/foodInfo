package com.example.foodinfo.data.local.room.model.pojo

import androidx.room.ColumnInfo
import androidx.room.Relation
import com.example.foodinfo.data.local.model.BasicOfRecipeMetadataDB
import com.example.foodinfo.data.local.model.BasicOfSearchFilterDB
import com.example.foodinfo.data.local.model.BasicOfSearchFilterExtendedDB
import com.example.foodinfo.data.local.room.model.entity.BasicOfRecipeMetadataEntity


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
        entityColumn = BasicOfRecipeMetadataDB.Columns.ID
    )
    override val metadata: BasicOfRecipeMetadataEntity?

) : BasicOfSearchFilterExtendedDB(
    ID = ID,
    infoID = infoID,
    filterName = filterName,
    minValue = minValue,
    maxValue = maxValue,
    metadata = metadata
)