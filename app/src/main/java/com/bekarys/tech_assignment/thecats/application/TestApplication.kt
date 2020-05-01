package com.bekarys.tech_assignment.thecats.application

import android.app.Application
import com.bekarys.tech_assignment.thecats.di.app.AppModule
import com.bekarys.tech_assignment.thecats.di.app.DaggerAppComponent

class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupDI()
    }

    private fun setupDI() {
        val component = DaggerAppComponent.builder()
            .appModule(AppModule(this)).build()
        component.inject(this)

        ApplicationComponentHolder.component = component
    }
}