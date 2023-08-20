package com.savestudents.features.mvp


interface BaseView<out T : BasePresenter> {
    val presenter: T
}