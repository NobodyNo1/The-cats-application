package com.bekarys.tech_assignment.thecats.features.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bekarys.tech_assignment.thecats.features.breedlist.data.local.BreedDao
import com.bekarys.tech_assignment.thecats.features.breedlist.data.model.storage.BreedEntry
import com.bekarys.tech_assignment.thecats.features.breedlist.data.local.BreedImageDao
import com.bekarys.tech_assignment.thecats.features.breedlist.data.model.storage.BreedImageEntry


@Database(
    entities = [
        BreedEntry::class,
        BreedImageEntry::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun breeds(): BreedDao

    abstract fun breedImage(): BreedImageDao
}