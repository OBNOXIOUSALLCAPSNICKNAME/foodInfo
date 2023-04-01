package com.example.foodinfo.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.foodinfo.local.dto.NutrientOfRecipeDB
import com.example.foodinfo.local.dto.RecipeDB


@Entity(
    tableName = NutrientOfRecipeDB.TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = RecipeEntity::class,
        parentColumns = arrayOf(RecipeDB.Columns.ID),
        childColumns = arrayOf(NutrientOfRecipeDB.Columns.RECIPE_ID),
        onUpdate = ForeignKey.NO_ACTION,
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class NutrientOfRecipeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Columns.ID)
    override val ID: Int = 0,

    @ColumnInfo(name = Columns.RECIPE_ID, index = true)
    override val recipeID: String,

    @ColumnInfo(name = Columns.INFO_ID)
    override val infoID: Int,

    @ColumnInfo(name = Columns.VALUE)
    override val value: Float

) : NutrientOfRecipeDB(
    ID = ID,
    recipeID = recipeID,
    infoID = infoID,
    value = value
) {

    companion object {
        operator fun invoke(item: NutrientOfRecipeDB): NutrientOfRecipeEntity {
            return NutrientOfRecipeEntity(
                ID = item.ID,
                recipeID = item.recipeID,
                infoID = item.infoID,
                value = item.value
            )
        }
    }
}