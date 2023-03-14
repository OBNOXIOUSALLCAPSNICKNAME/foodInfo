package com.example.foodinfo.di.module

import android.content.Context
import com.example.foodinfo.utils.PrefUtils
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
    fun providePrefUtils(context: Context): PrefUtils {
        return PrefUtils(context)
    }

    @Provides
    @Singleton
    @Named("Base")
    fun provideGson(): Gson = GsonBuilder().create()
}