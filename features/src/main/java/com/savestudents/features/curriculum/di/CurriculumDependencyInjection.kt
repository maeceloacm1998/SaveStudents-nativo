package com.savestudents.features.curriculum.di

import com.savestudents.features.curriculum.ui.CurriculumContract
import com.savestudents.features.curriculum.ui.CurriculumFragment
import com.savestudents.features.curriculum.ui.CurriculumPresenter
import org.koin.dsl.module

object CurriculumDependencyInjection {
    private val curriculumModules = module {
        factory<CurriculumContract.Presenter> { (view: CurriculumFragment) ->
            CurriculumPresenter(view, get(), get())
        }
    }

    val modules = arrayOf(curriculumModules)
}