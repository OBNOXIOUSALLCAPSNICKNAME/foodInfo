package com.example.foodinfo

import android.app.Application
import com.example.foodinfo.di.BaseViewModelFactory
import com.example.foodinfo.di.module.BaseApplicationModule
import com.example.foodinfo.local.room.DataBase
import com.example.foodinfo.repository.SearchFilterRepository
import com.example.foodinfo.utils.AssetProvider
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [BaseApplicationModule::class])
interface BaseApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun baseApplicationModule(baseApplicationModule: BaseApplicationModule): Builder

        fun build(): BaseApplicationComponent
    }

    fun inject(baseApplication: BaseApplication)

    fun viewModelsFactory(): BaseViewModelFactory

    val dataBase: DataBase

    val assetProvider: AssetProvider

    val searchFilterRepository: SearchFilterRepository
}