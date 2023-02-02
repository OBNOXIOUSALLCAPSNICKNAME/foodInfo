package com.example.foodinfo.di.module

import com.example.foodinfo.remote.api.RecipeAPI
import com.example.foodinfo.remote.api.RecipeAPIImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RemoteModule {
    @Singleton
    @Provides
    fun provideRecipeAPI(): RecipeAPI {
        return RecipeAPIImpl()
    }
}