package com.bekarys.tech_assignment.thecats.features.breedlist.data.model.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

private const val DEFAULT_BREED_ID: String = ""
private const val DEFAULT_IMAGE_ID: String = ""
private const val DEFAULT_NAME: String = ""
private const val DEFAULT_DESCRIPTION: String = ""
private const val DEFAULT_IMAGE: String = ""

@Entity(tableName = "breeds")
data class BreedEntry(
    @PrimaryKey
    val breedId: String = DEFAULT_BREED_ID,
    val name: String = DEFAULT_NAME,
    val imagePath: String = DEFAULT_IMAGE,
    val description: String = DEFAULT_DESCRIPTION,
    val imageId: String = DEFAULT_IMAGE_ID
)