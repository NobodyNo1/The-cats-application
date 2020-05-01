package com.bekarys.tech_assignment.thecats.di.shared.catlist

import com.bekarys.tech_assignment.thecats.di.scopes.FragmentScope
import com.bekarys.tech_assignment.thecats.features.breedlist.data.CatLocalDataSource
import com.bekarys.tech_assignment.thecats.features.breedlist.data.CatNetworkDataSource
import com.bekarys.tech_assignment.thecats.features.breedlist.data.DefaultCatRepository
import com.bekarys.tech_assignment.thecats.features.breedlist.domain.CatInteractor
import com.bekarys.tech_assignment.thecats.features.breedlist.domain.CatRepository
import com.bekarys.tech_assignment.thecats.features.common.api.cat.CatService
import com.bekarys.tech_assignment.thecats.features.common.database.AppDatabase
import com.bekarys.tech_assignment.thecats.features.common.downloadmanager.DownloadManagerController
import com.bekarys.tech_assignment.thecats.features.common.local.LocalDirectories
import com.bekarys.tech_assignment.thecats.features.common.network.RequestManager
import com.bekarys.tech_assignment.thecats.features.common.threading.AppScheduler
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class CatDataModule {

    @Singleton
    @Provides
    fun provideCatsRepository(
        okHttpClient: OkHttpClient,
        catService: CatService,
        requestManager: RequestManager,
        localDirectories: LocalDirectories,
        appDatabase: AppDatabase,
        appSchedulers: AppScheduler,
        downloadManagerController: DownloadManagerController
    ): CatRepository =
        DefaultCatRepository(
            CatNetworkDataSource(catService, requestManager, okHttpClient),
            CatLocalDataSource(localDirectories, appDatabase.breeds(), appDatabase.breedImage()),
            appSchedulers,
            downloadManagerController
        )

    @Singleton
    @Provides
    fun provideCatsInteractor(
        catRepository: CatRepository
    ): CatInteractor = CatInteractor(
        catRepository
    )
}