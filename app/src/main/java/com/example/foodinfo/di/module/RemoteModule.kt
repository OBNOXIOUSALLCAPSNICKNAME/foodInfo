package com.example.foodinfo.di.module

import com.example.foodinfo.BuildConfig
import com.example.foodinfo.remote.api.RecipeAPI
import com.example.foodinfo.remote.api.RecipeAttrAPI
import com.example.foodinfo.remote.response.ResponseAdapterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
class RemoteModule {

    @Singleton
    @Provides
    @Named("Edamam")
    fun provideRetrofitEdamam(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_EDAMAM)
            .addCallAdapterFactory(ResponseAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    @Named("GitHub")
    fun provideRetrofitGitHub(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_GITHUB)
            .addCallAdapterFactory(ResponseAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideApiRecipes(
        @Named("Edamam")
        retrofit: Retrofit
    ): RecipeAPI = retrofit.create(RecipeAPI::class.java)

    @Provides
    fun provideApiAttrs(
        @Named("GitHub")
        retrofit: Retrofit
    ): RecipeAttrAPI = retrofit.create(RecipeAttrAPI::class.java)

    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()
}