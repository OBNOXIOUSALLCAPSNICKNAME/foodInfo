package com.example.foodinfo.di.module

import com.example.foodinfo.remote.data_source.RecipeAttrRemoteSource
import com.example.foodinfo.remote.data_source.RecipeRemoteSource
import com.example.foodinfo.remote.retrofit.api.RecipeAPI
import com.example.foodinfo.remote.retrofit.api.RecipeAttrAPI
import com.example.foodinfo.remote.retrofit.data_source.RecipeAttrRetrofitSource
import com.example.foodinfo.remote.retrofit.data_source.RecipeRetrofitSource
import dagger.Module
import dagger.Provides


@Module
class RemoteSourceModule {
    @Provides
    fun provideRecipeRemote(recipeAPI: RecipeAPI): RecipeRemoteSource {
        return RecipeRetrofitSource(recipeAPI)
    }

    @Provides
    fun provideRecipeAttrRemote(recipeAttrAPI: RecipeAttrAPI): RecipeAttrRemoteSource {
        return RecipeAttrRetrofitSource(recipeAttrAPI)
    }
}