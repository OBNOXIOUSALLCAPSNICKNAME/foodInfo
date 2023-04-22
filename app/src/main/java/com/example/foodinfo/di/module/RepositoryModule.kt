package com.example.foodinfo.di.module

import com.example.foodinfo.core.utils.PrefUtils
import com.example.foodinfo.data.local.data_source.*
import com.example.foodinfo.data.remote.data_source.RecipeMetadataRemoteSource
import com.example.foodinfo.data.remote.data_source.RecipeRemoteSource
import com.example.foodinfo.data.repository.*
import com.example.foodinfo.domain.repository.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRecipeRepository(
        recipeLocal: RecipeLocalSource,
        recipeRemote: RecipeRemoteSource
    ): RecipeRepository {
        return RecipeRepositoryImpl(recipeLocal, recipeRemote)
    }

    @Provides
    @Singleton
    fun provideRecipeMetadataRepository(
        recipeMetadataLocal: RecipeMetadataLocalSource,
        recipeMetadataRemote: RecipeMetadataRemoteSource
    ): RecipeMetadataRepository {
        return RecipeMetadataRepositoryImpl(recipeMetadataLocal, recipeMetadataRemote)
    }

    @Provides
    @Singleton
    fun provideAPICredentialsRepository(
        apiCredentialsLocal: APICredentialsLocalSource,
    ): APICredentialsRepository {
        return APICredentialsRepositoryImpl(apiCredentialsLocal)
    }

    @Provides
    @Singleton
    fun provideSearchFilterRepository(
        searchFilterLocal: SearchFilterLocalSource,
        prefUtils: PrefUtils
    ): SearchFilterRepository {
        return SearchFilterRepositoryImpl(searchFilterLocal, prefUtils)
    }

    @Provides
    @Singleton
    fun provideSearchInputRepository(
        searchHistoryLocal: SearchHistoryLocalSource
    ): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(searchHistoryLocal)
    }
}