package com.os.onlinefood.data.di

import android.app.Application
import com.os.onlinefood.AppController
import com.os.onlinefood.data.database.MainDatabase
import com.os.onlinefood.data.repo.MyRepo
import com.os.onlinefood.ui.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val MainVM = module {
    viewModel {
        MainViewModel(get(), get())
    }
}

val ApplicationModule = module {
    single { AppController.getAppInstance() }
}

val DatabaseModule = module {

    fun provideDatabase(application: Application): MainDatabase {
        return  MainDatabase.buildDatabase(application)
    }

    single { provideDatabase(androidApplication()) }
}

val NetworkDependency = module {
    single {
        MyRepo(get(),get())
    }
}