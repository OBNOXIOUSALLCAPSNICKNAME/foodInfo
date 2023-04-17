package com.example.foodinfo.di.module

import android.content.Context
import com.example.foodinfo.domain.repository.RecipeAttrRepository
import com.example.foodinfo.domain.repository.RecipeRepository
import com.example.foodinfo.domain.repository.SearchFilterRepository
import com.example.foodinfo.domain.repository.SearchHistoryRepository
import com.example.foodinfo.domain.use_case.RecipeUseCase
import com.example.foodinfo.domain.use_case.SearchFilterUseCase
import com.example.foodinfo.local.data_source.*
import com.example.foodinfo.remote.data_source.RecipeAttrRemoteSource
import com.example.foodinfo.remote.data_source.RecipeRemoteSource
import com.example.foodinfo.utils.PrefUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRecipeRepository(
        apiCredentialsLocal: APICredentialsLocalSource,
        recipeLocal: RecipeLocalSource,
        recipeRemote: RecipeRemoteSource,
        prefUtils: PrefUtils
    ): RecipeRepository {
        return RecipeRepository(apiCredentialsLocal, recipeLocal, recipeRemote, prefUtils)
    }

    @Provides
    @Singleton
    fun provideRecipeAttrRepository(
        apiCredentialsLocal: APICredentialsLocalSource,
        recipeAttrLocal: RecipeAttrLocalSource,
        recipeAttrRemote: RecipeAttrRemoteSource,
        prefUtils: PrefUtils
    ): RecipeAttrRepository {
        return RecipeAttrRepository(apiCredentialsLocal, recipeAttrLocal, recipeAttrRemote, prefUtils)
    }

    @Provides
    @Singleton
    fun provideSearchFilterRepository(
        searchFilterLocal: SearchFilterLocalSource,
        prefUtils: PrefUtils
    ): SearchFilterRepository {
        return SearchFilterRepository(searchFilterLocal, prefUtils)
    }

    @Provides
    @Singleton
    fun provideSearchInputRepository(
        searchHistoryLocal: SearchHistoryLocalSource
    ): SearchHistoryRepository {
        return SearchHistoryRepository(searchHistoryLocal)
    }

    @Provides
    @Singleton
    fun provideRecipeUseCase(
        recipeAttrRepository: RecipeAttrRepository,
        recipeRepository: RecipeRepository
    ): RecipeUseCase {
        return RecipeUseCase(recipeAttrRepository, recipeRepository)
    }

    @Provides
    @Singleton
    fun provideSearchFilterUseCase(
        recipeAttrRepository: RecipeAttrRepository,
        searchFilterRepository: SearchFilterRepository,
        context: Context
    ): SearchFilterUseCase {
        return SearchFilterUseCase(
            recipeAttrRepository,
            searchFilterRepository,
            context
        )
    }
}