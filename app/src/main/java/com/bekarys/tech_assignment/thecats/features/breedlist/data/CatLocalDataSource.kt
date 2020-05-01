package com.bekarys.tech_assignment.thecats.features.breedlist.data

import android.graphics.Bitmap
import com.bekarys.tech_assignment.thecats.features.breedlist.data.local.BreedDao
import com.bekarys.tech_assignment.thecats.features.breedlist.data.model.storage.BreedEntry
import com.bekarys.tech_assignment.thecats.features.breedlist.data.local.BreedImageDao
import com.bekarys.tech_assignment.thecats.features.breedlist.data.model.storage.BreedImageEntry
import com.bekarys.tech_assignment.thecats.features.common.exceptions.customexceptions.NoStoredData
import com.bekarys.tech_assignment.thecats.features.common.local.LocalDirectories
import com.bekarys.tech_assignment.thecats.features.common.data.ResponseData
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class CatLocalDataSource(
    private val localDirectories: LocalDirectories,
    private val breedDao: BreedDao,
    private val breedImageDao: BreedImageDao
) {

    fun getFavoriteBreeds(): ResponseData<List<BreedEntry>> {
        val breeds = breedDao.getBreedList()
        if (breeds.isEmpty())
            return ResponseData.Fail(NoStoredData("breed table is empty"))
        return ResponseData.Success(
            breeds
        )
    }

    fun addFavoriteBreed(breed: BreedEntry) {
        breedDao.insert(breed)
    }

    fun getLocalImage(imageId: String): ResponseData<BreedImageEntry> {
        val images =
            breedImageDao.getBreedImage(imageId)
        if (images.isEmpty())
            return ResponseData.Fail(NoStoredData("image table is empty"))
        //Boundary save and show only one image per breed
        return ResponseData.Success(
            images.first()
        )
    }

    fun addImage(imageEntry: BreedImageEntry) {
        breedImageDao.insert(imageEntry)
    }

    fun getLocalImages(): ResponseData<List<BreedImageEntry>> {
        val breedImageList = breedImageDao.getBreedImageList()
        if (breedImageList.isEmpty())
            return ResponseData.Fail(NoStoredData("image table is empty"))
        //Boundary save and show only one image per breed
        return ResponseData.Success(
            breedImageList
        )
    }

    fun storeImage(filename: String, bitmap: Bitmap): ResponseData<String> {
        val f = File(localDirectories.downloadDirectory, filename)

        return try {
            val out = FileOutputStream(
                f
            )
            bitmap.compress(
                Bitmap.CompressFormat.JPEG,
                100,
                out
            )
            out.flush()
            out.close()
            return ResponseData.Success(f.path)
        } catch (e: FileNotFoundException) {
            ResponseData.Fail(IllegalStateException(e))
        } catch (e: IOException) {
            ResponseData.Fail(IllegalStateException(e))
        }
    }

    fun removeFavorite(breedId: String) {
        breedDao.deleteById(breedId)
    }
}