package com.bekarys.tech_assignment.thecats.features.common.threading

import com.bekarys.tech_assignment.thecats.features.common.threading.AppScheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.Scheduler

class DefaultAppScheduler :
    AppScheduler {

    override fun getIOScheduler(): Scheduler = Schedulers.io()

    override fun getUIScheduler(): Scheduler = AndroidSchedulers.mainThread()

    override fun getComputationScheduler(): Scheduler = Schedulers.computation()
}