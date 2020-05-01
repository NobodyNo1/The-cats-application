package com.bekarys.tech_assignment.thecats.features.favorites.presentation.presenter

import com.bekarys.tech_assignment.thecats.features.breedlist.domain.CatUseCase
import com.bekarys.tech_assignment.thecats.features.breedlist.domain.model.BreedData
import com.bekarys.tech_assignment.thecats.features.common.data.ResponseData
import com.bekarys.tech_assignment.thecats.features.common.threading.TestAppScheduler
import com.bekarys.tech_assignment.thecats.features.favorites.presentation.view.FavoritesView
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class TestFavoritesPresenter {

    private val mockCatUseCase: CatUseCase = Mockito.mock(CatUseCase::class.java)
    private val breedListView: FavoritesView = Mockito.mock(FavoritesView::class.java)
    private val breedListPresenter: FavoritesPresenter =
        FavoritesPresenter(mockCatUseCase, TestAppScheduler(Schedulers.trampoline()))

    @Test
    fun `test success favorites loading`() {
        breedListPresenter.attachView(breedListView)

        val expectedResponse = ResponseData.Success<List<BreedData>>(emptyList())
        Mockito.`when`(mockCatUseCase.getFavoriteBreedList()).thenReturn(expectedResponse)

        breedListPresenter.loadFavorites()
        Mockito.verify(
            breedListView,
            Mockito.atMostOnce()
        ).onFavoriteDataLoaded(expectedResponse.data)
        Mockito.verify(
            breedListView,
            Mockito.atMostOnce()
        ).onLoading(true)
        Mockito.verify(
            breedListView,
            Mockito.atMostOnce()
        ).onLoading(false)
        Mockito.verify(
            breedListView,
            Mockito.never()
        ).onError(Mockito.anyString())
    }

    @Test
    fun `test fail favorites loading`() {
        breedListPresenter.attachView(breedListView)

        val expectedMessage = "unique"
        val expectedResponse =
            ResponseData.Fail<List<BreedData>>(IllegalArgumentException(expectedMessage))
        Mockito.`when`(mockCatUseCase.getFavoriteBreedList()).thenReturn(expectedResponse)

        breedListPresenter.loadFavorites()
        Mockito.verify(
            breedListView,
            Mockito.never()
        ).onFavoriteDataLoaded(Mockito.anyList())
        Mockito.verify(
            breedListView,
            Mockito.atMostOnce()
        ).onLoading(true)
        Mockito.verify(
            breedListView,
            Mockito.atMostOnce()
        ).onLoading(false)
        Mockito.verify(
            breedListView,
            Mockito.never()
        ).onError(expectedMessage)
    }

}