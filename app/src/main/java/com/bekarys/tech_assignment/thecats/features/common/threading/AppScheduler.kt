package com.bekarys.tech_assignment.thecats.features.common.threading

import io.reactivex.Scheduler

interface AppScheduler {

    fun getIOScheduler(): Scheduler

    fun getUIScheduler(): Scheduler

    fun getComputationScheduler(): Scheduler
}