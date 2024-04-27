package com.savestudents.features.curriculum.data.di

import com.savestudents.features.curriculum.data.CurriculumRepository
import com.savestudents.features.curriculum.data.CurriculumRepositoryImpl
import com.savestudents.features.curriculum.domain.GetMattersUseCase
import com.savestudents.features.curriculum.ui.CurriculumContract
import com.savestudents.features.curriculum.ui.CurriculumFragment
import com.savestudents.features.curriculum.ui.CurriculumPresenter
import org.koin.dsl.module

object CurriculumDependencyInjection {
    private val curriculumModules = module {
        factory<CurriculumRepository> {
            CurriculumRepositoryImpl(
                client = get(),
                accountManager = get()
            )
        }

        factory { GetMattersUseCase(curriculumRepository = get())}

        factory<CurriculumContract.Presenter> { (view: CurriculumFragment) ->
            CurriculumPresenter(
                view = view,
                client = get(),
                accountManager = get(),
                getMattersUseCase = get()
            )
        }
    }

    val modules = arrayOf(curriculumModules)
}