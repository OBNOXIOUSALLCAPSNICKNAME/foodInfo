package com.example.foodinfo.local.room.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.foodinfo.local.model.LabelOfRecipeDB
import com.example.foodinfo.local.model.RecipeDB


@Entity(
    tableName = LabelOfRecipeDB.TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = RecipeEntity::class,
        parentColumns = arrayOf(RecipeDB.Columns.ID),
        childColumns = arrayOf(LabelOfRecipeDB.Columns.RECIPE_ID),
        onUpdate = ForeignKey.NO_ACTION,
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class LabelOfRecipeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Columns.ID)
    override val ID: Int = 0,

    @ColumnInfo(name = Columns.RECIPE_ID, index = true)
    override val recipeID: String,

    @ColumnInfo(name = Columns.INFO_ID)
    override val infoID: Int

) : LabelOfRecipeDB(
    ID = ID,
    recipeID = recipeID,
    infoID = infoID
) {
    companion object {
        operator fun invoke(item: LabelOfRecipeDB): LabelOfRecipeEntity {
            return LabelOfRecipeEntity(
                ID = item.ID,
                recipeID = item.recipeID,
                infoID = item.infoID
            )
        }
    }
}