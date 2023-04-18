package com.example.foodinfo.di.module

import android.content.Context
import com.example.foodinfo.data.local.room.DataBase
import com.example.foodinfo.data.local.room.dao.*
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
    fun provideSearchHistoryDAO(database: DataBase): SearchHistoryDAO {
        return database.searchHistoryDAO
    }

    @Provides
    fun provideRecipeAttrsDAO(database: DataBase): RecipeAttrDAO {
        return database.recipeAttrDAO
    }

    @Provides
    fun provideAPICredentialsDAO(database: DataBase): APICredentialsDAO {
        return database.apiCredentialsDAO
    }
}