package com.bekarys.tech_assignment.thecats.di.favorites

import com.bekarys.tech_assignment.thecats.di.app.AppComponent
import com.bekarys.tech_assignment.thecats.di.catlist.CatDataModule
import com.bekarys.tech_assignment.thecats.di.scopes.FragmentScope
import com.bekarys.tech_assignment.thecats.features.favorites.presentation.view.FavoritesFragment
import dagger.Component

@Component(
    modules = [
        CatDataModule::class,
        FavoritesModule::class
    ],
    dependencies = [
        AppComponent::class
    ]
)
@FragmentScope
interface FavoritesComponent {

    fun inject(fragment: FavoritesFragment)
}