package com.bekarys.tech_assignment.thecats.features.breedlist.data.model.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

private const val EMPTY_ID = ""
private const val EMPTY_PATH = ""

@Entity(tableName = "breed_image")
data class BreedImageEntry(
    @PrimaryKey
    val imageId: String = EMPTY_ID,
    val localImagePath: String = EMPTY_PATH
)