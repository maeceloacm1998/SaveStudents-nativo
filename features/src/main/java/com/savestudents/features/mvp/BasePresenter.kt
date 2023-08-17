package com.savestudents.features.mvp

interface BasePresenter<T> {
    fun start()
    var view: T
}