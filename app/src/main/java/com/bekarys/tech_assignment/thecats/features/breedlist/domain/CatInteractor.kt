package com.bekarys.tech_assignment.thecats.features.breedlist.domain

import android.graphics.Bitmap
import com.bekarys.tech_assignment.thecats.features.breedlist.domain.model.BreedData
import com.bekarys.tech_assignment.thecats.features.common.data.ResponseData

class CatInteractor(
    private val catRepository: CatRepository
) : CatUseCase {

    override fun getBreedList(page: Int): ResponseData<List<BreedData>> =
        catRepository.getBreedList(page)

    override fun getFavoriteBreedList(): ResponseData<List<BreedData>> =
        catRepository.getFavoriteBreedList()

    override fun addFavoriteBreed(breedData: BreedData) {
        val favoriteBreedList = catRepository.getFavoriteBreedList()
        if (favoriteBreedList is ResponseData.Success) {
            val foundItem = favoriteBreedList.data.firstOrNull { it.breedId == breedData.breedId }
            if (foundItem == null) {
                catRepository.addFavoriteBreed(breedData)
            }
        } else
            catRepository.addFavoriteBreed(breedData)
    }

//    override fun saveImage(imageId: String, serverImagePath: String): ResponseData<Boolean> {
//        val downloadImage = catRepository.downloadImage(serverImagePath)
//        return when (downloadImage) {
//            is ResponseData.Fail -> {
//                ResponseData.Fail(downloadImage.exception, downloadImage.message)
//            }
//            is ResponseData.Success -> {
//                val bitmap = BitmapFactory.decodeStream(downloadImage.data)
//                val filename = "${imageId}.jpg"
//                return saveImage(imageId, filename, bitmap)
//            }
//        }
//    }

    override fun saveImage(imageId: String, serverImagePath: String): ResponseData<Boolean> {
        catRepository.downloadImageViaManager(serverImagePath, "${imageId}.jpg")
        return ResponseData.Success(true)
    }

    override fun removeFavoriteBreed(data: BreedData) = catRepository.removeFavoriteBreed(data)

    private fun saveImage(
        imageId: String,
        filename: String,
        bitmap: Bitmap
    ): ResponseData<Boolean> {
        val imagePathResponse: ResponseData<String> = catRepository.storeImage(filename, bitmap)
        return when (imagePathResponse) {
            is ResponseData.Fail -> {
                return ResponseData.Fail(imagePathResponse.exception)
            }
            is ResponseData.Success -> {
                catRepository.addLocalImage(imageId, imagePathResponse.data)
                return ResponseData.Success(true)
            }
        }
    }

}