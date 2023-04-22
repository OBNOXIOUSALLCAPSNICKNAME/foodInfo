package com.example.foodinfo.di.module

import com.example.foodinfo.data.local.data_source.*
import com.example.foodinfo.data.local.room.dao.*
import com.example.foodinfo.data.local.room.data_source.*
import dagger.Module
import dagger.Provides


@Module
class LocalSourceModule {
    @Provides
    fun provideRecipeLocalSource(recipeDAO: RecipeDAO): RecipeLocalSource {
        return RecipeRoomSource(recipeDAO)
    }

    @Provides
    fun provideSearchFilterLocalSource(searchFilterDAO: SearchFilterDAO): SearchFilterLocalSource {
        return SearchFilterRoomSource(searchFilterDAO)
    }

    @Provides
    fun provideSearchHistoryLocalSource(searchHistoryDAO: SearchHistoryDAO): SearchHistoryLocalSource {
        return SearchHistoryRoomSource(searchHistoryDAO)
    }

    @Provides
    fun provideRecipeMetadataLocalSource(recipeMetadataDAO: RecipeMetadataDAO): RecipeMetadataLocalSource {
        return RecipeMetadataRoomSource(recipeMetadataDAO)
    }

    @Provides
    fun provideAPICredentialsLocalSource(apiCredentialsDAO: APICredentialsDAO): APICredentialsLocalSource {
        return APICredentialsRoomSource(apiCredentialsDAO)
    }
}