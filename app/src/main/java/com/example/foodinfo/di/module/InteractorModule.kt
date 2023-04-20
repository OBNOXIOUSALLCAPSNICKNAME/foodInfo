package com.example.foodinfo.di.module

import android.content.Context
import com.example.foodinfo.domain.interactor.RecipeInteractor
import com.example.foodinfo.domain.interactor.RecipeMetadataInteractor
import com.example.foodinfo.domain.interactor.SearchFilterInteractor
import com.example.foodinfo.domain.repository.APICredentialsRepository
import com.example.foodinfo.domain.repository.RecipeMetadataRepository
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
        recipeMetadataInteractor: RecipeMetadataInteractor,
        recipeRepository: RecipeRepository,
        prefUtils: PrefUtils
    ): RecipeInteractor {
        return RecipeInteractor(
            apiCredentialsRepository,
            recipeMetadataInteractor,
            recipeRepository,
            prefUtils
        )
    }

    @Provides
    @Singleton
    fun provideRecipeMetadataInteractor(
        apiCredentialsRepository: APICredentialsRepository,
        recipeMetadataRepository: RecipeMetadataRepository,
        prefUtils: PrefUtils
    ): RecipeMetadataInteractor {
        return RecipeMetadataInteractor(
            apiCredentialsRepository,
            recipeMetadataRepository,
            prefUtils
        )
    }

    @Provides
    @Singleton
    fun provideSearchFilterInteractor(
        searchFilterRepository: SearchFilterRepository,
        recipeMetadataInteractor: RecipeMetadataInteractor,
        context: Context,
    ): SearchFilterInteractor {
        return SearchFilterInteractor(
            searchFilterRepository,
            recipeMetadataInteractor,
            context
        )
    }
}