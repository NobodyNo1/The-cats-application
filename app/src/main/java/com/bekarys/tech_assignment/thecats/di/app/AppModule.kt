package com.bekarys.tech_assignment.thecats.di.app

import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.room.Room
import com.bekarys.tech_assignment.thecats.features.common.api.cat.CatService
import com.bekarys.tech_assignment.thecats.features.common.database.AppDatabase
import com.bekarys.tech_assignment.thecats.features.common.local.LocalDirectories
import com.bekarys.tech_assignment.thecats.features.common.network.DefaultNetworkStateManager
import com.bekarys.tech_assignment.thecats.features.common.network.NetworkStateManager
import com.bekarys.tech_assignment.thecats.features.common.network.RequestManager
import com.bekarys.tech_assignment.thecats.features.common.preferences.AppPreferences
import com.bekarys.tech_assignment.thecats.features.common.threading.AppScheduler
import com.bekarys.tech_assignment.thecats.features.common.threading.DefaultAppScheduler
import com.bekarys.tech_assignment.thecats.BuildConfig
import com.bekarys.tech_assignment.thecats.features.common.api.cat.CatServiceInterceptor
import com.bekarys.tech_assignment.thecats.features.common.downloadmanager.DownloadManagerController
import com.bekarys.tech_assignment.thecats.features.common.permission.PermissionHelper
import com.bekarys.tech_assignment.thecats.features.common.permission.PermissionManager
import com.bekarys.tech_assignment.thecats.features.common.permission.PermissionSessionManager
import com.bekarys.tech_assignment.thecats.features.common.preferences.DefaultAppPreferencesFacade
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class AppModule(
    private val application: Application
) {

    @Singleton
    @Provides
    fun provideContext(): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    @Singleton
    @Provides
    fun provideRequestBuilder(): Request.Builder = Request.Builder()

    @Singleton
    @Provides
    fun provideNetworkStateManager(): NetworkStateManager = DefaultNetworkStateManager(application)

    @Singleton
    @Provides
    fun provideAppScheduler(): AppScheduler = DefaultAppScheduler()

    @Singleton
    @Provides
    fun provideRequestManager(
        connectionManager: NetworkStateManager
    ): RequestManager = RequestManager(connectionManager)

    @Singleton
    @Provides
    fun provideLocalDirectories(context: Context): LocalDirectories = LocalDirectories(
        context.cacheDir.path,
        context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.path ?: ""
    )

    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "AppDatabase_1_0_0"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(appPreferences: AppPreferences): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.HEADERS
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(CatServiceInterceptor(appPreferences.getApiKey()))
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideService(
        okHttpClient: OkHttpClient,
        appPreferences: AppPreferences
    ): CatService {
        val retrofit = Retrofit.Builder()
            .baseUrl(appPreferences.getCatServiceBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(CatService::class.java)
    }

    @Singleton
    @Provides
    fun providePreferences(context: Context): AppPreferences {
        val appPreferences = AppPreferences(context)
        DefaultAppPreferencesFacade(appPreferences).setup()
        return appPreferences
    }

    @Singleton
    @Provides
    fun providePermissionPreferences(context: Context): PermissionSessionManager {
        return PermissionSessionManager(context)
    }

    @Singleton
    @Provides
    fun providePermissionHelper(
        context: Context,
        permissionSessionManager: PermissionSessionManager
    ): PermissionHelper {
        return PermissionHelper(context, PermissionManager(permissionSessionManager))
    }

    @Singleton
    @Provides
    fun downloadManagerController(
        context: Context
    ): DownloadManagerController {
        return DownloadManagerController((context.getSystemService(Context.DOWNLOAD_SERVICE) as? DownloadManager)!!)
    }
}