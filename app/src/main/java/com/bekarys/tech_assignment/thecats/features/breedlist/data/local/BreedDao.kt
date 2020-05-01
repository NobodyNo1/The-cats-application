package com.bekarys.tech_assignment.thecats.features.breedlist.data.local

import androidx.room.*
import com.bekarys.tech_assignment.thecats.features.breedlist.data.model.storage.BreedEntry


@Dao
interface BreedDao {

    @Transaction
    fun updateData(breedList: List<BreedEntry>) {
        deleteAllBreeds()
        insertAll(breedList)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<BreedEntry>)

    @Query("SELECT * FROM breeds")
    fun getBreedList(): List<BreedEntry>

    @Query("SELECT * FROM breeds WHERE breedId LIKE :breedId")
    fun getBreed(breedId: String): List<BreedEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(breedEntry: BreedEntry)

    @Delete
    fun delete(breedEntry: BreedEntry)

    @Query("DELETE FROM breeds WHERE breedId = :breedId")
    fun deleteById(breedId: String)

    @Update
    fun update(breedEntry: BreedEntry)

    @Query("DELETE FROM breeds")
    fun deleteAllBreeds()
}