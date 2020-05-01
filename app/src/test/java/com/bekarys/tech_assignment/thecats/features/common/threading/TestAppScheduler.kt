package com.bekarys.tech_assignment.thecats.features.common.threading

import io.reactivex.Scheduler

class TestAppScheduler(private val scheduler: Scheduler) : AppScheduler {

    override fun getIOScheduler(): Scheduler = scheduler

    override fun getUIScheduler(): Scheduler = scheduler

    override fun getComputationScheduler(): Scheduler = scheduler
}