package com.bekarys.tech_assignment.thecats.features.breedlist.domain.model

private const val DEFAULT_BREED_ID: String = ""
private const val DEFAULT_NAME: String = ""
private const val DEFAULT_DESCRIPTION: String = ""
private const val DEFAULT_IMAGE: String = ""
private const val DEFAULT_IMAGE_ID: String = ""
private const val DEFAULT_FAVORITE: Boolean = false

data class BreedData(
    val breedId: String = DEFAULT_BREED_ID,
    val name: String = DEFAULT_NAME,
    val serverImagePath: String = DEFAULT_IMAGE,
    val localImagePath: String = DEFAULT_IMAGE,
    val description: String = DEFAULT_DESCRIPTION,
    val imageId: String = DEFAULT_IMAGE_ID,
    var isFavorite: Boolean = DEFAULT_FAVORITE
)