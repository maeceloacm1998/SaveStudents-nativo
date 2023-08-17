package com.savestudents.features.mvp

import android.view.LayoutInflater
import android.view.ViewGroup

interface BaseView<out T : BasePresenter<*>> {
    val presenter: T
}