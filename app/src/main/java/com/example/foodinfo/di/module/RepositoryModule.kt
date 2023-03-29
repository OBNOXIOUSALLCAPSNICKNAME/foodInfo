package com.example.foodinfo.di.module

import android.content.Context
import com.example.foodinfo.local.dao.*
import com.example.foodinfo.remote.api.RecipeAPI
import com.example.foodinfo.remote.api.RecipeAttrAPI
import com.example.foodinfo.repository.RecipeAttrRepository
import com.example.foodinfo.repository.RecipeRepository
import com.example.foodinfo.repository.SearchFilterRepository
import com.example.foodinfo.repository.SearchHistoryRepository
import com.example.foodinfo.repository.use_case.RecipeUseCase
import com.example.foodinfo.repository.use_case.SearchFilterUseCase
import com.example.foodinfo.utils.PrefUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRecipeRepository(
        APICredentialsDAO: APICredentialsDAO,
        recipeDAO: RecipeDAO,
        recipeAPI: RecipeAPI,
        prefUtils: PrefUtils
    ): RecipeRepository {
        return RecipeRepository(APICredentialsDAO, recipeDAO, recipeAPI, prefUtils)
    }

    @Provides
    @Singleton
    fun provideRecipeAttrRepository(
        apiCredentialsDAO: APICredentialsDAO,
        recipeAttrDAO: RecipeAttrDAO,
        recipeAttrAPI: RecipeAttrAPI,
        prefUtils: PrefUtils
    ): RecipeAttrRepository {
        return RecipeAttrRepository(apiCredentialsDAO, recipeAttrDAO, recipeAttrAPI, prefUtils)
    }

    @Provides
    @Singleton
    fun provideSearchFilterRepository(
        searchFilterDAO: SearchFilterDAO,
        prefUtils: PrefUtils
    ): SearchFilterRepository {
        return SearchFilterRepository(searchFilterDAO, prefUtils)
    }

    @Provides
    @Singleton
    fun provideSearchInputRepository(
        searchHistoryDAO: SearchHistoryDAO
    ): SearchHistoryRepository {
        return SearchHistoryRepository(searchHistoryDAO)
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
        apiCredentialsDAO: APICredentialsDAO,
        recipeAttrRepository: RecipeAttrRepository,
        searchFilterRepository: SearchFilterRepository,
        prefUtils: PrefUtils,
        context: Context
    ): SearchFilterUseCase {
        return SearchFilterUseCase(
            apiCredentialsDAO,
            recipeAttrRepository,
            searchFilterRepository,
            prefUtils,
            context
        )
    }
}