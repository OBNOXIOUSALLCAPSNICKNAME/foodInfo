package com.example.foodinfo.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.foodinfo.local.dto.IngredientOfRecipeDB
import com.example.foodinfo.local.dto.RecipeDB


@Entity(
    tableName = IngredientOfRecipeDB.TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = RecipeEntity::class,
        parentColumns = arrayOf(RecipeDB.Columns.ID),
        childColumns = arrayOf(IngredientOfRecipeDB.Columns.RECIPE_ID),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )]
)
data class IngredientOfRecipeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Columns.ID)
    override val ID: Int = 0,

    @ColumnInfo(name = Columns.RECIPE_ID)
    override val recipeID: String,

    @ColumnInfo(name = Columns.TEXT)
    override val text: String,

    @ColumnInfo(name = Columns.QUANTITY)
    override val quantity: Float,

    @ColumnInfo(name = Columns.MEASURE)
    override val measure: String,

    @ColumnInfo(name = Columns.WEIGHT)
    override val weight: Float,

    @ColumnInfo(name = Columns.FOOD)
    override val food: String,

    @ColumnInfo(name = Columns.FOOD_CATEGORY)
    override val foodCategory: String,

    @ColumnInfo(name = Columns.FOOD_ID)
    override val foodID: String,

    @ColumnInfo(name = Columns.PREVIEW_URL)
    override val previewURL: String

) : IngredientOfRecipeDB(
    ID = ID,
    recipeID = recipeID,
    text = text,
    quantity = quantity,
    measure = measure,
    weight = weight,
    food = food,
    foodCategory = foodCategory,
    foodID = foodID,
    previewURL = previewURL
) {

    companion object {
        fun fromDB(item: IngredientOfRecipeDB): IngredientOfRecipeEntity {
            return IngredientOfRecipeEntity(
                ID = item.ID,
                recipeID = item.recipeID,
                text = item.text,
                quantity = item.quantity,
                measure = item.measure,
                weight = item.weight,
                food = item.food,
                foodCategory = item.foodCategory,
                foodID = item.foodID,
                previewURL = item.previewURL
            )
        }
    }
}