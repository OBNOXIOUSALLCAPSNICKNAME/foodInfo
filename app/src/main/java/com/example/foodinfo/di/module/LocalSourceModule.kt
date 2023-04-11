package com.example.foodinfo.di.module

import com.example.foodinfo.local.data_source.*
import com.example.foodinfo.local.room.dao.*
import com.example.foodinfo.local.room.data_source.*
import dagger.Module
import dagger.Provides


@Module
class LocalSourceModule {
    @Provides
    fun provideRecipeLocal(recipeDAO: RecipeDAO): RecipeLocalSource {
        return RecipeRoomSource(recipeDAO)
    }

    @Provides
    fun provideSearchFilterLocal(searchFilterDAO: SearchFilterDAO): SearchFilterLocalSource {
        return SearchFilterRoomSource(searchFilterDAO)
    }

    @Provides
    fun provideSearchHistoryLocal(searchHistoryDAO: SearchHistoryDAO): SearchHistoryLocalSource {
        return SearchHistoryRoomSource(searchHistoryDAO)
    }

    @Provides
    fun provideRecipeAttrsLocal(recipeAttrDAO: RecipeAttrDAO): RecipeAttrLocalSource {
        return RecipeAttrRoomSource(recipeAttrDAO)
    }

    @Provides
    fun provideAPICredentialsLocal(apiCredentialsDAO: APICredentialsDAO): APICredentialsLocalSource {
        return APICredentialsRoomSource(apiCredentialsDAO)
    }
}