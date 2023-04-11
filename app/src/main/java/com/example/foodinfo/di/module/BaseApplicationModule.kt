package com.example.foodinfo.di.module

import dagger.Module
import dagger.android.AndroidInjectionModule


@Module(
    includes = [
        ContextModule::class,
        UtilsModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        ContextModule::class,
        LocalSourceModule::class,
        RoomModule::class,
        RemoteSourceModule::class,
        RetrofitModule::class,
        AndroidInjectionModule::class
    ]
)
class BaseApplicationModule