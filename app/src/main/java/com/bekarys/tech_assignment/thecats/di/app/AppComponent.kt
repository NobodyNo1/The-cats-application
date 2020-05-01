package com.bekarys.tech_assignment.thecats.di.app

import android.content.Context
import com.google.gson.Gson
import com.bekarys.tech_assignment.thecats.application.TestApplication
import com.bekarys.tech_assignment.thecats.di.shared.catlist.CatDataModule
import com.bekarys.tech_assignment.thecats.features.breedlist.domain.CatInteractor
import com.bekarys.tech_assignment.thecats.features.breedlist.domain.CatRepository
import com.bekarys.tech_assignment.thecats.features.common.api.cat.CatService
import com.bekarys.tech_assignment.thecats.features.common.database.AppDatabase
import com.bekarys.tech_assignment.thecats.features.common.downloadmanager.DownloadManagerController
import com.bekarys.tech_assignment.thecats.features.common.local.LocalDirectories
import com.bekarys.tech_assignment.thecats.features.common.network.NetworkStateManager
import com.bekarys.tech_assignment.thecats.features.common.network.RequestManager
import com.bekarys.tech_assignment.thecats.features.common.permission.PermissionHelper
import com.bekarys.tech_assignment.thecats.features.common.threading.AppScheduler
import dagger.Component
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Singleton


@Component(
    modules = [
        AppModule::class, CatDataModule::class
    ]
)
@Singleton
interface AppComponent {

    fun inject(application: TestApplication)

    fun okHttpClient(): OkHttpClient

    fun gson(): Gson

    fun requestBuilder(): Request.Builder

    fun context(): Context

    fun networkStateManager(): NetworkStateManager

    fun appScheduler(): AppScheduler

    fun requestManager(): RequestManager

    fun localDirectories(): LocalDirectories

    fun appDatabase(): AppDatabase

    fun catService(): CatService

    fun permissionHelper(): PermissionHelper

    fun downloadManagerController(): DownloadManagerController

    fun catRepository(): CatRepository

    fun catInteractor(): CatInteractor
}
