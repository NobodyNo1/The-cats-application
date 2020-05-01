package com.bekarys.tech_assignment.thecats.di.main

import com.bekarys.tech_assignment.thecats.main.MainActivity
import com.bekarys.tech_assignment.thecats.di.app.AppComponent
import com.bekarys.tech_assignment.thecats.di.scopes.ActivityScope
import dagger.Component

@Component(
    dependencies = [AppComponent::class]
)
@ActivityScope
interface MainComponent {

    fun inject(mainActivity: MainActivity)
}
