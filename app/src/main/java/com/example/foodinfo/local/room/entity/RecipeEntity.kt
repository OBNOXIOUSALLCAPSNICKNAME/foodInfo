package com.example.foodinfo.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.foodinfo.local.dto.RecipeDB
import com.example.foodinfo.local.dto.RecipeToSaveDB


@Entity(
    tableName = RecipeDB.TABLE_NAME,
    indices = [Index(value = arrayOf(RecipeDB.Columns.ID), unique = true)]
)
data class RecipeEntity(
    @PrimaryKey
    @ColumnInfo(name = Columns.ID)
    override val ID: String,

    @ColumnInfo(name = Columns.SOURCE)
    override val source: String,

    @ColumnInfo(name = Columns.NAME)
    override val name: String,

    @ColumnInfo(name = Columns.PREVIEW_URL)
    override val previewURL: String,

    @ColumnInfo(name = Columns.CALORIES)
    override val calories: Int,

    @ColumnInfo(name = Columns.INGREDIENTS_COUNT)
    override val ingredientsCount: Int,

    @ColumnInfo(name = Columns.WEIGHT)
    override val weight: Int,

    @ColumnInfo(name = Columns.COOKING_TIME)
    override val cookingTime: Int,

    @ColumnInfo(name = Columns.SERVINGS)
    override val servings: Int,

    @ColumnInfo(name = Columns.IS_FAVORITE)
    override val isFavorite: Boolean,

    @ColumnInfo(name = Columns.LAST_UPDATE)
    override val lastUpdate: Long

) : RecipeDB(
    ID = ID,
    source = source,
    name = name,
    previewURL = previewURL,
    calories = calories,
    ingredientsCount = ingredientsCount,
    weight = weight,
    cookingTime = cookingTime,
    servings = servings,
    isFavorite = isFavorite,
    lastUpdate = lastUpdate
) {

    companion object {
        operator fun invoke(item: RecipeDB): RecipeEntity {
            return RecipeEntity(
                ID = item.ID,
                source = item.source,
                name = item.name,
                previewURL = item.previewURL,
                calories = item.calories,
                ingredientsCount = item.ingredientsCount,
                weight = item.weight,
                cookingTime = item.cookingTime,
                servings = item.servings,
                isFavorite = item.isFavorite,
                lastUpdate = item.lastUpdate
            )
        }

        operator fun invoke(item: RecipeToSaveDB): RecipeEntity {
            return RecipeEntity(
                ID = item.ID,
                source = item.source,
                name = item.name,
                previewURL = item.previewURL,
                calories = item.calories,
                ingredientsCount = item.ingredientsCount,
                weight = item.weight,
                cookingTime = item.cookingTime,
                servings = item.servings,
                isFavorite = false,
                lastUpdate = System.currentTimeMillis()
            )
        }
    }
}