package com.savestudents.features.addMatter.di

import com.savestudents.features.addMatter.ui.AddMatterContract
import com.savestudents.features.addMatter.ui.AddMatterFragment
import com.savestudents.features.addMatter.ui.AddMatterPresenter
import org.koin.dsl.module

object AddMatterDependencyInjection {
    private val accountRegisterModules = module {
        factory<AddMatterContract.Presenter> { (view: AddMatterFragment) ->
            AddMatterPresenter(view, get())
        }
    }

    val modules = arrayOf(accountRegisterModules)
}