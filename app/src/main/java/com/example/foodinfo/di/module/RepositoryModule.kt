package com.example.foodinfo.di.module

import com.example.foodinfo.local.dao.RecipeAttrDAO
import com.example.foodinfo.local.dao.RecipeDAO
import com.example.foodinfo.local.dao.SearchFilterDAO
import com.example.foodinfo.local.dao.SearchHistoryDAO
import com.example.foodinfo.remote.api.RecipeAPI
import com.example.foodinfo.remote.api.RecipeAttrAPI
import com.example.foodinfo.repository.RecipeAttrRepository
import com.example.foodinfo.repository.RecipeRepository
import com.example.foodinfo.repository.SearchFilterRepository
import com.example.foodinfo.repository.SearchHistoryRepository
import com.example.foodinfo.repository.use_case.RecipeUseCase
import com.example.foodinfo.repository.use_case.SearchFilterUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRecipeRepository(
        recipeDAO: RecipeDAO,
        recipeAPI: RecipeAPI
    ): RecipeRepository {
        return RecipeRepository(recipeDAO, recipeAPI)
    }

    @Provides
    @Singleton
    fun provideSearchFilterRepository(
        searchFilterDAO: SearchFilterDAO
    ): SearchFilterRepository {
        return SearchFilterRepository(searchFilterDAO)
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
    fun provideRecipeAttrRepository(
        recipeAttrDAO: RecipeAttrDAO,
        recipeAttrAPI: RecipeAttrAPI,
    ): RecipeAttrRepository {
        return RecipeAttrRepository(recipeAttrDAO, recipeAttrAPI)
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
        searchFilterRepository: SearchFilterRepository
    ): SearchFilterUseCase {
        return SearchFilterUseCase(recipeAttrRepository, searchFilterRepository)
    }
}