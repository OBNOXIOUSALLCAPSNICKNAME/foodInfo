package com.example.foodinfo.di.module

import android.content.Context
import com.example.foodinfo.local.dao.RecipeAttrDAO
import com.example.foodinfo.local.dao.RecipeDAO
import com.example.foodinfo.local.dao.SearchFilterDAO
import com.example.foodinfo.local.dao.SearchHistoryDAO
import com.example.foodinfo.remote.api.RecipeAPI
import com.example.foodinfo.repository.RecipeAttrRepository
import com.example.foodinfo.repository.RecipeRepository
import com.example.foodinfo.repository.SearchFilterRepository
import com.example.foodinfo.repository.SearchHistoryRepository
import com.example.foodinfo.repository.impl.RecipeAttrRepositoryImpl
import com.example.foodinfo.repository.impl.RecipeRepositoryImpl
import com.example.foodinfo.repository.impl.SearchFilterRepositoryImpl
import com.example.foodinfo.repository.impl.SearchHistoryRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepositoryRecipes(
        context: Context,
        recipeDAO: RecipeDAO,
        recipeAPI: RecipeAPI
    ): RecipeRepository {
        return RecipeRepositoryImpl(context, recipeDAO, recipeAPI)
    }

    @Provides
    @Singleton
    fun provideRepositorySearchFilter(
        searchFilterDAO: SearchFilterDAO,
        recipeAttrDao: RecipeAttrDAO,
    ): SearchFilterRepository {
        return SearchFilterRepositoryImpl(searchFilterDAO, recipeAttrDao)
    }

    @Provides
    @Singleton
    fun provideRepositorySearchInput(
        searchHistoryDAO: SearchHistoryDAO
    ): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(searchHistoryDAO)
    }

    @Provides
    @Singleton
    fun provideRepositoryRepositoryRecipeFieldsInfo(
        recipeAttrDao: RecipeAttrDAO
    ): RecipeAttrRepository {
        return RecipeAttrRepositoryImpl(recipeAttrDao)
    }
}