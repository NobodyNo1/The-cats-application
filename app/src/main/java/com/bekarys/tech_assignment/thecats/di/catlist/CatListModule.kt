package com.bekarys.tech_assignment.thecats.di.catlist

import com.bekarys.tech_assignment.thecats.di.scopes.FragmentScope
import com.bekarys.tech_assignment.thecats.features.breedlist.domain.CatInteractor
import com.bekarys.tech_assignment.thecats.features.breedlist.domain.CatUseCase
import com.bekarys.tech_assignment.thecats.features.breedlist.presentation.presenter.BreedListPresenter
import com.bekarys.tech_assignment.thecats.features.common.threading.AppScheduler
import dagger.Module
import dagger.Provides

@Module
class CatListModule {

    @FragmentScope
    @Provides
    fun provideCatsUseCase(
        catsInteractor: CatInteractor
    ): CatUseCase = catsInteractor

    @FragmentScope
    @Provides
    fun provideCatListPresenter(
        catUseCase: CatUseCase,
        appScheduler: AppScheduler
    ): BreedListPresenter = BreedListPresenter(catUseCase, appScheduler)

}