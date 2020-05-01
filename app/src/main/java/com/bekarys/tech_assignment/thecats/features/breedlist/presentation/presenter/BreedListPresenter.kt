package com.bekarys.tech_assignment.thecats.features.breedlist.presentation.presenter

import com.bekarys.tech_assignment.thecats.features.breedlist.domain.CatUseCase
import com.bekarys.tech_assignment.thecats.features.breedlist.domain.model.BreedData
import com.bekarys.tech_assignment.thecats.features.breedlist.presentation.view.BreedListView
import com.bekarys.tech_assignment.thecats.features.breedlist.presentation.view.EmptyBreedListView
import com.bekarys.tech_assignment.thecats.features.common.base.BasePresenter
import com.bekarys.tech_assignment.thecats.features.common.model.PagingInfo
import com.bekarys.tech_assignment.thecats.features.common.threading.AppScheduler
import com.bekarys.tech_assignment.thecats.features.common.data.ResponseData
import io.reactivex.Observable

private val emptyView = EmptyBreedListView
private val emptyPagingInfo = PagingInfo(false, -1)

class BreedListPresenter(
    private val catUseCase: CatUseCase,
    private val appScheduler: AppScheduler
) : BasePresenter<BreedListView>() {

    private var activePagingInfo: PagingInfo = emptyPagingInfo
    private var loadingPagingInfo: PagingInfo = emptyPagingInfo


    fun loadInitialPage() {
        view.onLoading(true)
        loadData(PagingInfo()) {
            view.onInitialDataLoaded(it)
        }
    }

    fun loadNextPage() {
        loadData(activePagingInfo.copy(currentPage = activePagingInfo.currentPage + 1)) {
            view.onNextPageLoaded(it)
        }
    }

    private fun loadData(
        pagingInfo: PagingInfo,
        onComplete: (data: List<BreedData>) -> Unit
    ) {
        if (isLoading())
            return
        loadingPagingInfo = pagingInfo
        val disposableProductRequest = Observable.fromCallable {
            catUseCase.getBreedList(loadingPagingInfo.currentPage)
        }.subscribeOn(appScheduler.getIOScheduler())
            .observeOn(appScheduler.getUIScheduler())
            .subscribe({
                view.onLoading(false)
                when (it) {
                    is ResponseData.Fail -> view.onError(it.message)
                    is ResponseData.Success -> {
                        onComplete(it.data)
                        activePagingInfo = loadingPagingInfo.copy()
                        loadingPagingInfo = emptyPagingInfo
                    }
                }
            }) {
                //Every exception should be handled in respective layer and should return ResponseData.Fail
                throw it
            }
        compositeDisposable.add(disposableProductRequest)
    }

    fun isLoading() = loadingPagingInfo != emptyPagingInfo

    fun addFavorite(data: BreedData) {
        val disposable = Observable.fromCallable {
            catUseCase.addFavoriteBreed(data)
        }.subscribeOn(appScheduler.getIOScheduler())
            .observeOn(appScheduler.getUIScheduler())
            .subscribe()
        compositeDisposable.add(disposable)
    }

    fun removeFavorite(data: BreedData) {
        val disposable = Observable.fromCallable {
            catUseCase.removeFavoriteBreed(data)
        }.subscribeOn(appScheduler.getIOScheduler())
            .observeOn(appScheduler.getUIScheduler())
            .subscribe()
        compositeDisposable.add(disposable)
    }

    fun downloadImage(data: BreedData) {
        val disposable = Observable.fromCallable {
            catUseCase.saveImage(data.imageId, data.serverImagePath)
        }.subscribeOn(appScheduler.getIOScheduler())
            .observeOn(appScheduler.getUIScheduler())
            .subscribe()
        compositeDisposable.add(disposable)
    }

    override fun getEmptyView(): BreedListView = emptyView
}