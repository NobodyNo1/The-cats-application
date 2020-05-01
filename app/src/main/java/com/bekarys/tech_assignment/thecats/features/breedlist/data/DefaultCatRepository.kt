package com.bekarys.tech_assignment.thecats.features.breedlist.data

import android.graphics.Bitmap
import com.bekarys.tech_assignment.thecats.features.breedlist.data.model.storage.BreedEntry
import com.bekarys.tech_assignment.thecats.features.breedlist.data.model.storage.BreedImageEntry
import com.bekarys.tech_assignment.thecats.features.breedlist.domain.CatRepository
import com.bekarys.tech_assignment.thecats.features.breedlist.domain.model.BreedData
import com.bekarys.tech_assignment.thecats.features.common.cache.KeyValueCacheHelper
import com.bekarys.tech_assignment.thecats.features.common.cache.KeyValueData
import com.bekarys.tech_assignment.thecats.features.common.cache.ListCacheHelper
import com.bekarys.tech_assignment.thecats.features.common.downloadmanager.DownloadManagerController
import com.bekarys.tech_assignment.thecats.features.common.threading.AppScheduler
import com.bekarys.tech_assignment.thecats.features.common.data.ResponseData
import io.reactivex.Observable
import java.io.InputStream
import java.util.concurrent.CountDownLatch

private const val DEFAULT_IMAGE_PATH: String = ""

class DefaultCatRepository(
    private val networkDataStorage: CatNetworkDataSource,
    private val localDataSource: CatLocalDataSource,
    private val appSchedulers: AppScheduler,
    private val downloadManagerController: DownloadManagerController
) : CatRepository {

    private val imageCache: KeyValueCacheHelper<String, String> = KeyValueCacheHelper()
    private val favoriteCache: ListCacheHelper<BreedData> = ListCacheHelper()

    fun updateLocalImageCache() {
        val localImages = localDataSource.getLocalImages()
        if (localImages is ResponseData.Success)
            imageCache.updateCache(localImages.data.map {
                KeyValueData(it.imageId, it.localImagePath)
            })
    }

    override fun getBreedList(page: Int): ResponseData<List<BreedData>> {
        val breedList = networkDataStorage.getBreedList(page)
        return when (breedList) {
            is ResponseData.Fail -> {
                return ResponseData.Fail(breedList.exception)
            }
            is ResponseData.Success -> {
                val data = preparedData(breedList.data.map {
                    BreedData(
                        breedId = it.id,
                        name = it.name,
                        description = it.description
                    )
                })
                val finalData = evaluateFavoriteFlags(data)
                ResponseData.Success(finalData)
            }
        }

    }

    private fun evaluateFavoriteFlags(data: List<BreedData>): List<BreedData> {
        val favorites = getFavoriteBreedList()
        if (favorites !is ResponseData.Success)
            return data
        val favoritesIds = favorites.data.map { it.breedId }
        return data.map {
            if (favoritesIds.contains(it.breedId))
                it.copy(isFavorite = true)
            else
                it
        }
    }

    private fun preparedData(
        breedList: List<BreedData>
    ): List<BreedData> {
        val latch: CountDownLatch = CountDownLatch(breedList.size)
        val result: MutableList<BreedData> = mutableListOf()
        Observable.fromIterable(breedList)
            .observeOn(appSchedulers.getComputationScheduler())
            .flatMap {
                println(
                    "Received " + it.breedId + " on thread "
                            + Thread.currentThread().getName()
                )
                Observable.fromCallable {
                    val images = networkDataStorage.getImages(breedId = it.breedId)
                    if (images is ResponseData.Success && images.data.isNotEmpty()) {
                        val imageData = images.data.first()
                        it.copy(
                            imageId = imageData.id,
                            serverImagePath = imageData.url
                        )
                    } else
                        it.copy()
                }
            }
            .subscribe {
                result.add(it)
                latch.countDown()
            }
        latch.await()

        return result
    }

    override fun addFavoriteBreed(breedData: BreedData) {
        val breedEntry = BreedEntry(
            breedId = breedData.breedId,
            name = breedData.name,
            imageId = breedData.imageId,
            imagePath = breedData.serverImagePath,
            description = breedData.description
        )
        localDataSource.addFavoriteBreed(breedEntry)
        favoriteCache.addToCache(breedData)
    }

    override fun getFavoriteBreedList(): ResponseData<List<BreedData>> {
        if (favoriteCache.actual())
            return ResponseData.Success(favoriteCache.getElements())

        val favoriteBreedResponse = localDataSource.getFavoriteBreeds()
        return when (favoriteBreedResponse) {
            is ResponseData.Fail -> {
                ResponseData.Fail(
                    favoriteBreedResponse.exception,
                    favoriteBreedResponse.message
                )
            }
            is ResponseData.Success -> {
                val favoriteBreeds = favoriteBreedResponse.data.map {
                    var localImagePath = DEFAULT_IMAGE_PATH
                    val localImage = getLocalImage(it.imageId)
                    if (localImage is ResponseData.Success)
                        localImagePath = localImage.data
                    BreedData(
                        breedId = it.breedId,
                        name = it.name,
                        serverImagePath = it.imagePath,
                        description = it.description,
                        imageId = it.imageId,
                        localImagePath = localImagePath,
                        isFavorite = true
                    )
                }
                favoriteCache.updateCache(favoriteBreeds)
                ResponseData.Success(favoriteBreeds)
            }
        }
    }

    override fun addLocalImage(imageId: String, localImagePath: String) {
        localDataSource.addImage(
            BreedImageEntry(
                imageId = imageId,
                localImagePath = localImagePath
            )
        )
        imageCache.addToCache(KeyValueData(imageId, localImagePath))
    }

    override fun downloadImage(serverImagePath: String): ResponseData<InputStream> =
        networkDataStorage.downloadImage(serverImagePath)

    override fun downloadImageViaManager(
        serverImagePath: String,
        filename: String
    ) {
        downloadManagerController.startDownload(
            url = serverImagePath,
            filename = filename
        )
    }


    override fun storeImage(filename: String, bitmap: Bitmap): ResponseData<String> =
        localDataSource.storeImage(filename, bitmap)

    override fun removeFavoriteBreed(data: BreedData) {
        localDataSource.removeFavorite(data.breedId)
        favoriteCache.removeFromCache(favoriteCache.getElements { data.breedId == it.breedId }.first())
    }

    private fun getLocalImage(imageId: String): ResponseData<String> {
        if (imageCache.actual()) {
            val cacheImage = imageCache.getElements {
                it.key == imageId
            }
            if (cacheImage.isNotEmpty())
                return ResponseData.Success(cacheImage.first().value)
        }

        val localImageReponse = localDataSource.getLocalImage(imageId)
        return when (localImageReponse) {
            is ResponseData.Fail -> {
                ResponseData.Fail(localImageReponse.exception, localImageReponse.message)
            }
            is ResponseData.Success -> {
                val localImage = localImageReponse.data.localImagePath
                imageCache.addToCache(KeyValueData(imageId, localImage))
                ResponseData.Success(localImage)
            }
        }
    }

    fun clearCache() {
        imageCache.clearCache()
        favoriteCache.clearCache()
    }
}