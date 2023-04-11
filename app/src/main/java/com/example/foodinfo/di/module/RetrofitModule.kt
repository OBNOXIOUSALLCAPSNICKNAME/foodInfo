package com.example.foodinfo.di.module

import com.example.foodinfo.BuildConfig
import com.example.foodinfo.remote.retrofit.GitHubTypeAdapterFactory
import com.example.foodinfo.remote.retrofit.api.RecipeAPI
import com.example.foodinfo.remote.retrofit.api.RecipeAttrAPI
import com.example.foodinfo.remote.retrofit.response_adapter.ResponseAdapterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
class RetrofitModule {

    @Singleton
    @Provides
    @Named("Edamam")
    fun provideRetrofitEdamam(
        @Named("Base")
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_EDAMAM)
            .addCallAdapterFactory(ResponseAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    @Named("GitHub")
    fun provideRetrofitGitHub(
        @Named("GitHub")
        gson: Gson
    ): Retrofit {
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
    @Singleton
    @Named("GitHub")
    fun provideGson(
        @Named("Base")
        baseGson: Gson
    ): Gson = GsonBuilder()
        .registerTypeAdapterFactory(GitHubTypeAdapterFactory(baseGson))
        .create()
}