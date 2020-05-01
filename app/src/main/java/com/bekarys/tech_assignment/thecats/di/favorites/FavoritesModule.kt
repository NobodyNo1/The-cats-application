package com.bekarys.tech_assignment.thecats.di.favorites

import com.bekarys.tech_assignment.thecats.di.scopes.FragmentScope
import com.bekarys.tech_assignment.thecats.features.breedlist.domain.CatInteractor
import com.bekarys.tech_assignment.thecats.features.breedlist.domain.CatUseCase
import com.bekarys.tech_assignment.thecats.features.common.threading.AppScheduler
import com.bekarys.tech_assignment.thecats.features.favorites.presentation.presenter.FavoritesPresenter
import dagger.Module
import dagger.Provides

@Module
class FavoritesModule {

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
    ): FavoritesPresenter = FavoritesPresenter(catUseCase, appScheduler)

}
