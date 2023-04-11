package com.example.foodinfo.local.room.model.pojo

import androidx.room.ColumnInfo
import androidx.room.Relation
import com.example.foodinfo.local.model.NutrientOfRecipeDB
import com.example.foodinfo.local.model.NutrientOfRecipeExtendedDB
import com.example.foodinfo.local.model.NutrientRecipeAttrDB
import com.example.foodinfo.local.room.model.entity.NutrientRecipeAttrEntity


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
        entityColumn = NutrientRecipeAttrDB.Columns.ID
    )
    override val attrInfo: NutrientRecipeAttrEntity?

) : NutrientOfRecipeExtendedDB(
    ID = ID,
    recipeID = recipeID,
    infoID = infoID,
    value = value,
    attrInfo = attrInfo
)