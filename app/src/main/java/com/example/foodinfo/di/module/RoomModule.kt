package com.example.foodinfo.di.module

import android.content.Context
import com.example.foodinfo.local.dao.*
import com.example.foodinfo.local.room.DataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule {
    @Singleton
    @Provides
    fun provideDataBase(context: Context): DataBase {
        return DataBase.getDatabase(context)
    }


    @Provides
    fun provideRecipeDAO(database: DataBase): RecipeDAO {
        return database.recipeDAO
    }

    @Provides
    fun provideSearchFilterDAO(database: DataBase): SearchFilterDAO {
        return database.searchFilterDAO
    }

    @Provides
    fun provideSearchInputHistoryDAO(database: DataBase): SearchHistoryDAO {
        return database.searchHistoryDAO
    }

    @Provides
    fun provideRecipeFieldsInfoDAO(database: DataBase): RecipeAttrDAO {
        return database.recipeAttrDao
    }

    @Provides
    fun provideAPICredentialsDAO(database: DataBase): APICredentialsDAO {
        return database.apiCredentialsDao
    }
}