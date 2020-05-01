package com.bekarys.tech_assignment.thecats.features.favorites.presentation.presenter

import com.bekarys.tech_assignment.thecats.features.breedlist.domain.CatUseCase
import com.bekarys.tech_assignment.thecats.features.breedlist.domain.model.BreedData
import com.bekarys.tech_assignment.thecats.features.common.base.BasePresenter
import com.bekarys.tech_assignment.thecats.features.common.threading.AppScheduler
import com.bekarys.tech_assignment.thecats.features.favorites.presentation.view.EmptyFavoritesView
import com.bekarys.tech_assignment.thecats.features.favorites.presentation.view.FavoritesView
import com.bekarys.tech_assignment.thecats.features.common.data.ResponseData
import io.reactivex.Observable

private val emptyView = EmptyFavoritesView

class FavoritesPresenter(
    private val catUseCase: CatUseCase,
    private val appScheduler: AppScheduler
) : BasePresenter<FavoritesView>() {

    private var loading: Boolean = false

    fun loadFavorites() {
        if (isLoading())
            return
        setLoading(true)
        val disposableProductRequest = Observable.fromCallable {
            catUseCase.getFavoriteBreedList()
        }.subscribeOn(appScheduler.getIOScheduler())
            .observeOn(appScheduler.getUIScheduler())
            .subscribe({
                setLoading(false)
                when (it) {
                    is ResponseData.Fail -> view.onError(it.message)
                    is ResponseData.Success -> {
                        view.onFavoriteDataLoaded(it.data)
                    }
                }
            }) {
                //Every exception should be handled in respective layer and should return ResponseData.Fail
                throw it
            }
        compositeDisposable.add(disposableProductRequest)
    }

    private fun setLoading(loading: Boolean) {
        this.loading = loading
        view.onLoading(loading)
    }

    fun isLoading() = loading

    fun removeFavorite(data: BreedData) {
        val disposable = Observable.fromCallable {
            catUseCase.removeFavoriteBreed(data)
        }.subscribeOn(appScheduler.getIOScheduler())
            .observeOn(appScheduler.getUIScheduler())
            .subscribe()
        compositeDisposable.add(disposable)
    }

    fun addFavorite(data: BreedData) {
        val disposable = Observable.fromCallable {
            catUseCase.addFavoriteBreed(data)
        }.subscribeOn(appScheduler.getIOScheduler())
            .observeOn(appScheduler.getUIScheduler())
            .subscribe()
        compositeDisposable.add(disposable)
    }

    override fun getEmptyView(): FavoritesView = emptyView

    fun downloadImage(data: BreedData) {
        val disposable = Observable.fromCallable {
            catUseCase.saveImage(data.imageId, data.serverImagePath)
        }.subscribeOn(appScheduler.getIOScheduler())
            .observeOn(appScheduler.getUIScheduler())
            .subscribe()
        compositeDisposable.add(disposable)
    }
}