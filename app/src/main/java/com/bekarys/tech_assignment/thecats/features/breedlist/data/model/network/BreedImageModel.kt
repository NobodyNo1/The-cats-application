package com.bekarys.tech_assignment.thecats.features.breedlist.data.model.network

import com.google.gson.annotations.SerializedName

private val EMPTY_BREED = EmptyHolder.EMPTY_BREED
private const val EMPTY_ID = ""
private const val EMPTY_URL = ""
private const val EMPTY_WIDTH = 0
private const val EMPTY_HEIGHT = 0

class BreedImageModel(
    @SerializedName("breeds")
    var breeds: List<BreedModel> = EMPTY_BREED,
    @SerializedName("id")
    var id: String = EMPTY_ID,
    @SerializedName("url")
    var url: String = EMPTY_URL,
    @SerializedName("width")
    var width: Int = EMPTY_WIDTH,
    @SerializedName("height")
    var height: Int = EMPTY_HEIGHT
)