package com.example.foodinfo.di.module

import com.example.foodinfo.data.remote.data_source.RecipeMetadataRemoteSource
import com.example.foodinfo.data.remote.data_source.RecipeRemoteSource
import com.example.foodinfo.data.remote.retrofit.api.RecipeAPI
import com.example.foodinfo.data.remote.retrofit.api.RecipeMetadataAPI
import com.example.foodinfo.data.remote.retrofit.data_source.RecipeMetadataRetrofitSource
import com.example.foodinfo.data.remote.retrofit.data_source.RecipeRetrofitSource
import dagger.Module
import dagger.Provides


@Module
class RemoteSourceModule {
    @Provides
    fun provideRecipeRemote(recipeAPI: RecipeAPI): RecipeRemoteSource {
        return RecipeRetrofitSource(recipeAPI)
    }

    @Provides
    fun provideRecipeMetadataRemote(recipeMetadataAPI: RecipeMetadataAPI): RecipeMetadataRemoteSource {
        return RecipeMetadataRetrofitSource(recipeMetadataAPI)
    }
}