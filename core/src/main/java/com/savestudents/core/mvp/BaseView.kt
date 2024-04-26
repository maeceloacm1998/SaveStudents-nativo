package com.savestudents.core.mvp


interface BaseView<out T : BasePresenter> {
    val presenter: T
}