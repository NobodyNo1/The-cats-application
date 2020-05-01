package com.bekarys.tech_assignment.thecats.features.breedlist.domain

import android.graphics.Bitmap
import com.bekarys.tech_assignment.thecats.features.breedlist.domain.model.BreedData
import com.bekarys.tech_assignment.thecats.features.common.data.ResponseData
import java.io.InputStream

interface CatRepository {

    fun getBreedList(page: Int): ResponseData<List<BreedData>>

    fun getFavoriteBreedList(): ResponseData<List<BreedData>>

    fun addFavoriteBreed(breedData: BreedData)

    fun addLocalImage(imageId: String, localImagePath: String)

    fun downloadImage(serverImagePath: String): ResponseData<InputStream>

    fun downloadImageViaManager(
        serverImagePath: String,
        filename: String
    )

    fun storeImage(filename: String, bitmap: Bitmap): ResponseData<String>

    fun removeFavoriteBreed(data: BreedData)
}