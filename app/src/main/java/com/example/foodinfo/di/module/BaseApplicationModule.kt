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
        RoomModule::class,
        RemoteModule::class,
        AndroidInjectionModule::class
    ]
)
class BaseApplicationModule