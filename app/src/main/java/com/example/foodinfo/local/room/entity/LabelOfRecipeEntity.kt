package com.example.foodinfo.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.foodinfo.local.dto.LabelOfRecipeDB
import com.example.foodinfo.local.dto.RecipeDB


@Entity(
    tableName = LabelOfRecipeDB.TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = RecipeEntity::class,
        parentColumns = arrayOf(RecipeDB.Columns.ID),
        childColumns = arrayOf(LabelOfRecipeDB.Columns.RECIPE_ID),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )]
)
data class LabelOfRecipeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Columns.ID)
    override val ID: Int = 0,

    @ColumnInfo(name = Columns.RECIPE_ID)
    override val recipeID: String,

    @ColumnInfo(name = Columns.INFO_ID)
    override val infoID: Int

) : LabelOfRecipeDB(
    ID = ID,
    recipeID = recipeID,
    infoID = infoID
) {

    companion object {
        fun toEntity(item: LabelOfRecipeDB): LabelOfRecipeEntity {
            return LabelOfRecipeEntity(
                ID = item.ID,
                recipeID = item.recipeID,
                infoID = item.infoID
            )
        }
    }
}