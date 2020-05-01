package com.bekarys.tech_assignment.thecats.di.catlist

import com.bekarys.tech_assignment.thecats.di.app.AppComponent
import com.bekarys.tech_assignment.thecats.di.scopes.FragmentScope
import com.bekarys.tech_assignment.thecats.features.breedlist.presentation.view.BreedListFragment
import dagger.Component

@Component(
    modules = [
        CatListModule::class
    ],
    dependencies = [
        AppComponent::class
    ]
)
@FragmentScope
interface CatListComponent {

    fun inject(fragment: BreedListFragment)
}