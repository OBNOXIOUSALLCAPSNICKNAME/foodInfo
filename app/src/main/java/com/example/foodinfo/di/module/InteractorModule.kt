package com.example.foodinfo.di.module

import com.example.foodinfo.domain.interactor.RecipeAttrsInteractor
import com.example.foodinfo.domain.interactor.RecipeInteractor
import com.example.foodinfo.domain.interactor.SearchFilterInteractor
import com.example.foodinfo.domain.repository.APICredentialsRepository
import com.example.foodinfo.domain.repository.RecipeAttrRepository
import com.example.foodinfo.domain.repository.RecipeRepository
import com.example.foodinfo.domain.repository.SearchFilterRepository
import com.example.foodinfo.utils.PrefUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class InteractorModule {

    @Provides
    @Singleton
    fun provideRecipeInteractor(
        apiCredentialsRepository: APICredentialsRepository,
        recipeAttrsInteractor: RecipeAttrsInteractor,
        recipeRepository: RecipeRepository,
        prefUtils: PrefUtils
    ): RecipeInteractor {
        return RecipeInteractor(
            apiCredentialsRepository,
            recipeAttrsInteractor,
            recipeRepository,
            prefUtils
        )
    }

    @Provides
    @Singleton
    fun provideRecipeAttrsInteractor(
        apiCredentialsRepository: APICredentialsRepository,
        recipeAttrRepository: RecipeAttrRepository,
        prefUtils: PrefUtils
    ): RecipeAttrsInteractor {
        return RecipeAttrsInteractor(
            apiCredentialsRepository,
            recipeAttrRepository,
            prefUtils
        )
    }

    @Provides
    @Singleton
    fun provideSearchFilterInteractor(
        searchFilterRepository: SearchFilterRepository,
        recipeAttrsInteractor: RecipeAttrsInteractor
    ): SearchFilterInteractor {
        return SearchFilterInteractor(
            searchFilterRepository,
            recipeAttrsInteractor
        )
    }
}