package com.bekarys.tech_assignment.thecats.features.breedlist.presentation.presenter

import com.bekarys.tech_assignment.thecats.features.breedlist.domain.CatUseCase
import com.bekarys.tech_assignment.thecats.features.breedlist.domain.model.BreedData
import com.bekarys.tech_assignment.thecats.features.breedlist.presentation.view.BreedListView
import com.bekarys.tech_assignment.thecats.features.common.data.ResponseData
import com.bekarys.tech_assignment.thecats.features.common.threading.TestAppScheduler
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.verification.VerificationMode

@RunWith(JUnit4::class)
class TestBreedListPresenter {

    private val mockCatUseCase: CatUseCase = Mockito.mock(CatUseCase::class.java)
    private val breedListView: BreedListView = Mockito.mock(BreedListView::class.java)
    private val breedListPresenter: BreedListPresenter =
        BreedListPresenter(mockCatUseCase, TestAppScheduler(Schedulers.trampoline()))

    @Test
    fun `test success initial data loading`() {
        breedListPresenter.attachView(breedListView)

        val expectedResponse = ResponseData.Success<List<BreedData>>(emptyList())
        Mockito.`when`(mockCatUseCase.getBreedList(0)).thenReturn(expectedResponse)

        breedListPresenter.loadInitialPage()
        Mockito.verify(
            breedListView,
            Mockito.atMostOnce()
        ).onInitialDataLoaded(expectedResponse.data)
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
    fun `test fail initial data loading`() {
        breedListPresenter.attachView(breedListView)

        val expectedResponse = ResponseData.Fail<List<BreedData>>(IllegalStateException(""))
        Mockito.`when`(mockCatUseCase.getBreedList(0)).thenReturn(expectedResponse)

        breedListPresenter.loadInitialPage()
        Mockito.verify(
            breedListView,
            Mockito.never()
        ).onInitialDataLoaded(Mockito.anyList())
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
            Mockito.atMostOnce()
        ).onError("")
    }


    @Test
    fun `test pagination data loading`() {
        breedListPresenter.attachView(breedListView)

        val expectedResponseForInitial = ResponseData.Success<List<BreedData>>(listOf(BreedData(breedId = "1")))
        Mockito.`when`(mockCatUseCase.getBreedList(0)).thenReturn(expectedResponseForInitial)

        val expectedResponseForNextPage = ResponseData.Success<List<BreedData>>(listOf(BreedData(breedId = "2")))
        Mockito.`when`(mockCatUseCase.getBreedList(1)).thenReturn(expectedResponseForNextPage)

        breedListPresenter.loadInitialPage()
        breedListPresenter.loadNextPage()
        Mockito.verify(
            breedListView,
            Mockito.atMostOnce()
        ).onInitialDataLoaded(expectedResponseForInitial.data)
        Mockito.verify(
            breedListView,
            Mockito.atMostOnce()
        ).onLoading(true)
        Mockito.verify(
            breedListView,
            Mockito.never()
        ).onError(Mockito.anyString())

        Mockito.verify(
            breedListView,
            Mockito.atMostOnce()
        ).onNextPageLoaded(expectedResponseForNextPage.data)
    }
}