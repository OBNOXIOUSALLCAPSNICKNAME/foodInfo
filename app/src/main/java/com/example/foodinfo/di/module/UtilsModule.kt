package com.example.foodinfo.di.module

import android.content.Context
import com.example.foodinfo.utils.AssetProvider
import com.example.foodinfo.utils.PrefUtils
import com.example.foodinfo.utils.ResourcesProvider
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton


@Module
class UtilsModule {

    @Provides
    @Singleton
    fun provideAssetProvider(context: Context): AssetProvider {
        return AssetProvider(context)
    }

    @Provides
    @Singleton
    fun provideResourcesProvider(context: Context): ResourcesProvider {
        return ResourcesProvider(context)
    }

    @Provides
    @Singleton
    fun providePrefUtils(context: Context): PrefUtils {
        return PrefUtils(context)
    }

    @Provides
    @Singleton
    @Named("Base")
    fun provideGson(): Gson = GsonBuilder().create()
}