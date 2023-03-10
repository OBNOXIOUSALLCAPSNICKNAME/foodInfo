package com.example.foodinfo.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.foodinfo.local.dto.APICredentialsDB


@Entity(
    tableName = APICredentialsDB.TABLE_NAME,
    indices = [Index(value = arrayOf(APICredentialsDB.Columns.NAME), unique = true)]
)
data class APICredentialsEntity(
    @PrimaryKey
    @ColumnInfo(name = Columns.NAME)
    override val name: String,

    @ColumnInfo(name = Columns.APP_ID_FOOD)
    override val appIDFood: String,

    @ColumnInfo(name = Columns.APP_ID_RECIPES)
    override val appIDRecipes: String,

    @ColumnInfo(name = Columns.APP_ID_NUTRITION)
    override val appIDNutrition: String,

    @ColumnInfo(name = Columns.APP_KEY_FOOD)
    override val appKeyFood: String,

    @ColumnInfo(name = Columns.APP_KEY_RECIPES)
    override val appKeyRecipes: String,

    @ColumnInfo(name = Columns.APP_KEY_NUTRITION)
    override val appKeyNutrition: String

) : APICredentialsDB(
    name = name,
    appIDFood = appIDFood,
    appIDRecipes = appIDRecipes,
    appIDNutrition = appIDNutrition,
    appKeyFood = appKeyFood,
    appKeyRecipes = appKeyRecipes,
    appKeyNutrition = appKeyNutrition
) {
    companion object {
        fun toEntity(item: APICredentialsDB): APICredentialsEntity {
            return APICredentialsEntity(
                name = item.name,
                appIDFood = item.appIDFood,
                appIDRecipes = item.appIDRecipes,
                appIDNutrition = item.appIDNutrition,
                appKeyFood = item.appKeyFood,
                appKeyRecipes = item.appKeyRecipes,
                appKeyNutrition = item.appKeyNutrition
            )
        }
    }
}