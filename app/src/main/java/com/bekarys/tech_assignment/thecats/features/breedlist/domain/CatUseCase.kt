package com.bekarys.tech_assignment.thecats.features.breedlist.domain

import com.bekarys.tech_assignment.thecats.features.breedlist.domain.model.BreedData
import com.bekarys.tech_assignment.thecats.features.common.data.ResponseData

interface CatUseCase {

    fun getBreedList(page: Int): ResponseData<List<BreedData>>

    fun getFavoriteBreedList(): ResponseData<List<BreedData>>

    fun addFavoriteBreed(breedData: BreedData)

    fun saveImage(imageId: String, serverImagePath: String): ResponseData<Boolean>

    fun removeFavoriteBreed(data: BreedData)
}