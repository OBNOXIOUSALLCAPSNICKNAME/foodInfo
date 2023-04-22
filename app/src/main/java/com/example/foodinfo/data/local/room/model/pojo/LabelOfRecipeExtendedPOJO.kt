package com.example.foodinfo.data.local.room.model.pojo

import androidx.room.ColumnInfo
import androidx.room.Relation
import com.example.foodinfo.data.local.model.LabelOfRecipeDB
import com.example.foodinfo.data.local.model.LabelOfRecipeExtendedDB
import com.example.foodinfo.data.local.model.LabelOfRecipeMetadataDB
import com.example.foodinfo.data.local.room.model.entity.LabelOfRecipeMetadataEntity


data class LabelOfRecipeExtendedPOJO(
    @ColumnInfo(name = LabelOfRecipeDB.Columns.ID)
    override val ID: Int = 0,

    @ColumnInfo(name = LabelOfRecipeDB.Columns.RECIPE_ID)
    override val recipeID: String,

    @ColumnInfo(name = LabelOfRecipeDB.Columns.INFO_ID)
    override val infoID: Int,

    @Relation(
        parentColumn = LabelOfRecipeDB.Columns.INFO_ID,
        entityColumn = LabelOfRecipeMetadataDB.Columns.ID,
        entity = LabelOfRecipeMetadataEntity::class
    )
    override val metadata: LabelRecipeMetadataExtendedPOJO?

) : LabelOfRecipeExtendedDB(
    ID = ID,
    recipeID = recipeID,
    infoID = infoID,
    metadata = metadata
)