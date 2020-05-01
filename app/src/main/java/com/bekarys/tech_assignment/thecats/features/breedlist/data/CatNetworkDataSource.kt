package com.bekarys.tech_assignment.thecats.features.breedlist.data

import com.bekarys.tech_assignment.thecats.features.breedlist.data.model.network.BreedModel
import com.bekarys.tech_assignment.thecats.features.breedlist.data.model.network.BreedImageModel
import com.bekarys.tech_assignment.thecats.features.common.api.cat.CatService
import com.bekarys.tech_assignment.thecats.features.common.network.RequestManager
import com.bekarys.tech_assignment.thecats.features.common.data.ResponseData
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.InputStream

class CatNetworkDataSource(
    private val catService: CatService,
    private val requestManager: RequestManager,
    private val okHttpClient: OkHttpClient
) {

    fun getBreedList(page: Int): ResponseData<List<BreedModel>> = requestManager.execute {
        catService.getCatList(page)
    }

    fun getImages(breedId: String): ResponseData<List<BreedImageModel>> = requestManager.execute {
        catService.getBreedImage(breedId)
    }

    fun downloadImage(serverImagePath: String): ResponseData<InputStream> {
        val executeRaw = requestManager.executeRaw {
            okHttpClient.newCall(Request.Builder().url(serverImagePath).build())
        }
        return when (executeRaw) {
            is ResponseData.Fail -> {
                return ResponseData.Fail(executeRaw.exception)
            }
            is ResponseData.Success -> {
                ResponseData.Success(executeRaw.data.byteStream())
            }
        }
    }

    fun downloadImageViaManager(serverImagePath: String): ResponseData<InputStream> {
        val executeRaw = requestManager.executeRaw {
            okHttpClient.newCall(Request.Builder().url(serverImagePath).build())
        }
        return when (executeRaw) {
            is ResponseData.Fail -> {
                return ResponseData.Fail(executeRaw.exception)
            }
            is ResponseData.Success -> {
                ResponseData.Success(executeRaw.data.byteStream())
            }
        }
    }
}
