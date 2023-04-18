package com.example.foodinfo.di.module

import com.example.foodinfo.data.local.data_source.*
import com.example.foodinfo.data.remote.data_source.RecipeAttrRemoteSource
import com.example.foodinfo.data.remote.data_source.RecipeRemoteSource
import com.example.foodinfo.data.repository.*
import com.example.foodinfo.domain.repository.APICredentialsRepository
import com.example.foodinfo.utils.PrefUtils
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
    ): RecipeRepositoryImpl {
        return RecipeRepositoryImpl(recipeLocal, recipeRemote)
    }

    @Provides
    @Singleton
    fun provideRecipeAttrRepository(
        recipeAttrLocal: RecipeAttrLocalSource,
        recipeAttrRemote: RecipeAttrRemoteSource
    ): RecipeAttrRepositoryImpl {
        return RecipeAttrRepositoryImpl(recipeAttrLocal, recipeAttrRemote)
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
    ): SearchFilterRepositoryImpl {
        return SearchFilterRepositoryImpl(searchFilterLocal, prefUtils)
    }

    @Provides
    @Singleton
    fun provideSearchInputRepository(
        searchHistoryLocal: SearchHistoryLocalSource
    ): SearchHistoryRepositoryImpl {
        return SearchHistoryRepositoryImpl(searchHistoryLocal)
    }
}