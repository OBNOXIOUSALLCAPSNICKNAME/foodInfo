package com.example.foodinfo.local.room.pojo

import androidx.room.ColumnInfo
import androidx.room.Relation
import com.example.foodinfo.local.dto.NutrientOfRecipeDB
import com.example.foodinfo.local.dto.NutrientOfRecipeExtendedDB
import com.example.foodinfo.local.dto.NutrientRecipeAttrDB
import com.example.foodinfo.local.room.entity.NutrientRecipeAttrEntity


data class NutrientOfRecipeExtendedPOJO(
    @ColumnInfo(name = NutrientOfRecipeDB.Columns.ID)
    override val ID: Int = 0,

    @ColumnInfo(name = NutrientOfRecipeDB.Columns.RECIPE_ID)
    override val recipeID: String,

    @ColumnInfo(name = NutrientOfRecipeDB.Columns.INFO_ID)
    override val infoID: Int,

    @ColumnInfo(name = NutrientOfRecipeDB.Columns.TOTAL_VALUE)
    override val totalValue: Float,

    @Relation(
        parentColumn = NutrientOfRecipeDB.Columns.INFO_ID,
        entityColumn = NutrientRecipeAttrDB.Columns.ID
    )
    override val attrInfo: NutrientRecipeAttrEntity

) : NutrientOfRecipeExtendedDB(
    ID = ID,
    recipeID = recipeID,
    infoID = infoID,
    totalValue = totalValue,
    attrInfo = attrInfo
)