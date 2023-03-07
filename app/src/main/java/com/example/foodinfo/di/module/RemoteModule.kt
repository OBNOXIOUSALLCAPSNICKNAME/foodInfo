package com.example.foodinfo.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides


@Module
class RemoteModule {
    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()
}