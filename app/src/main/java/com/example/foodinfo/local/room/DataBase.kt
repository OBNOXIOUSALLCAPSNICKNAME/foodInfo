package com.example.foodinfo.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodinfo.local.room.dao.*
import com.example.foodinfo.local.room.entity.*


@Database(
    exportSchema = false,
    version = 1,
    entities = [
        RecipeEntity::class,
        SearchFilterEntity::class,
        SearchInputEntity::class,
        IngredientOfRecipeEntity::class,
        NutrientOfRecipeEntity::class,
        LabelOfRecipeEntity::class,
        LabelRecipeAttrEntity::class,
        CategoryRecipeAttrEntity::class,
        LabelOfSearchFilterEntity::class,
        NutrientOfSearchFilterEntity::class,
        NutrientRecipeAttrEntity::class,
        BasicRecipeAttrEntity::class,
        BasicOfSearchFilterEntity::class,
        EdamamCredentialsEntity::class,
        GitHubCredentialsEntity::class,
    ]
)
abstract class DataBase : RoomDatabase() {
    abstract val recipeDAO: RecipeDAORoom
    abstract val searchFilterDAO: SearchFilterDAORoom
    abstract val searchHistoryDAO: SearchHistoryDAORoom
    abstract val recipeAttrDao: RecipeAttrDAORoom
    abstract val apiCredentialsDao: APICredentialsDAORoom

    companion object {
        private const val DB_NAME = "data_base"

        private lateinit var dataBase: DataBase

        fun getDatabase(
            context: Context,
        ): DataBase {
            if (!Companion::dataBase.isInitialized)
                dataBase = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    DB_NAME
                )
                    .allowMainThreadQueries() // TODO replace after refactoring all DAO
                    .build()
            return dataBase
        }
    }
}