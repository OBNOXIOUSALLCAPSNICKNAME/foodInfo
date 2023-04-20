package com.example.foodinfo.data.local.room.model.pojo

import androidx.room.ColumnInfo
import androidx.room.Relation
import com.example.foodinfo.data.local.model.LabelOfRecipeDB
import com.example.foodinfo.data.local.model.LabelOfSearchFilterDB
import com.example.foodinfo.data.local.model.LabelOfSearchFilterExtendedDB
import com.example.foodinfo.data.local.model.LabelOfRecipeMetadataDB
import com.example.foodinfo.data.local.room.model.entity.LabelOfRecipeMetadataEntity


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
        entityColumn = LabelOfRecipeMetadataDB.Columns.ID,
        entity = LabelOfRecipeMetadataEntity::class
    )
    override val metadata: LabelRecipeMetadataExtendedPOJO?

) : LabelOfSearchFilterExtendedDB(
    ID = ID,
    filterName = filterName,
    infoID = infoID,
    isSelected = isSelected,
    metadata = metadata
)