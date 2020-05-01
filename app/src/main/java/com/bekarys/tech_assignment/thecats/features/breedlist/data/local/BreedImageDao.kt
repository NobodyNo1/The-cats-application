package com.bekarys.tech_assignment.thecats.features.breedlist.data.local

import androidx.room.*
import com.bekarys.tech_assignment.thecats.features.breedlist.data.model.storage.BreedImageEntry


@Dao
interface BreedImageDao {

    @Transaction
    fun updateData(breedImageList: List<BreedImageEntry>) {
        deleteAllBreeds()
        insertAll(breedImageList)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<BreedImageEntry>)

    @Query("SELECT * FROM breed_image")
    fun getBreedImageList(): List<BreedImageEntry>

    @Query("SELECT * FROM breed_image WHERE imageId LIKE :imageId")
    fun getBreedImage(imageId: String): List<BreedImageEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(breedImageEntry: BreedImageEntry)

    @Delete
    fun delete(breedImageEntry: BreedImageEntry)

    @Update
    fun update(breedImageEntry: BreedImageEntry)

    @Query("DELETE FROM breed_image")
    fun deleteAllBreeds()
}