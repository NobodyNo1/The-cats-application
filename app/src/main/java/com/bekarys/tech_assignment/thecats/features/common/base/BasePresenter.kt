package com.bekarys.tech_assignment.thecats.features.common.base

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<V : BaseView> {

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()
    lateinit var view: V

    fun attachView(view: V) {
        this.view = view
    }

    fun removeView() {
        view = getEmptyView()
    }

    abstract fun getEmptyView(): V
}