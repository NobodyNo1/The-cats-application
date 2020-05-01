package com.bekarys.tech_assignment.thecats.features.common.api.cat

import com.bekarys.tech_assignment.thecats.features.breedlist.data.model.network.BreedModel
import com.bekarys.tech_assignment.thecats.features.breedlist.data.model.network.BreedImageModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val BREED_ITEM_LIMIT = 20

interface CatService {

    @GET("breeds?limit=${BREED_ITEM_LIMIT}")
    fun getCatList(@Query("page") page: Int): Call<List<BreedModel>>

    @GET("images/search")
    fun getBreedImage(@Query("breed_id") breedId: String): Call<List<BreedImageModel>>
}