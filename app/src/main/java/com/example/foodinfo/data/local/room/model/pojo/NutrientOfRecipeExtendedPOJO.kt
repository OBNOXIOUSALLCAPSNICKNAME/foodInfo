package com.example.foodinfo.data.local.room.model.pojo

import androidx.room.ColumnInfo
import androidx.room.Relation
import com.example.foodinfo.data.local.model.NutrientOfRecipeDB
import com.example.foodinfo.data.local.model.NutrientOfRecipeExtendedDB
import com.example.foodinfo.data.local.model.NutrientOfRecipeMetadataDB
import com.example.foodinfo.data.local.room.model.entity.NutrientOfRecipeMetadataEntity


data class NutrientOfRecipeExtendedPOJO(
    @ColumnInfo(name = NutrientOfRecipeDB.Columns.ID)
    override val ID: Int = 0,

    @ColumnInfo(name = NutrientOfRecipeDB.Columns.RECIPE_ID)
    override val recipeID: String,

    @ColumnInfo(name = NutrientOfRecipeDB.Columns.INFO_ID)
    override val infoID: Int,

    @ColumnInfo(name = NutrientOfRecipeDB.Columns.VALUE)
    override val value: Float,

    @Relation(
        parentColumn = NutrientOfRecipeDB.Columns.INFO_ID,
        entityColumn = NutrientOfRecipeMetadataDB.Columns.ID
    )
    override val metadata: NutrientOfRecipeMetadataEntity?

) : NutrientOfRecipeExtendedDB(
    ID = ID,
    recipeID = recipeID,
    infoID = infoID,
    value = value,
    metadata = metadata
)