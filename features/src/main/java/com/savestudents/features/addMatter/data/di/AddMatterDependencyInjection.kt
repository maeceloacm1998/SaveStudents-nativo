package com.savestudents.features.addMatter.data.di

import com.savestudents.features.addMatter.data.AddMatterRepository
import com.savestudents.features.addMatter.data.AddMatterRepositoryImpl
import com.savestudents.features.addMatter.domain.CreateMatterOptionsUseCase
import com.savestudents.features.addMatter.domain.CreateMatterUseCase
import com.savestudents.features.addMatter.domain.GetMatterOptionsUseCase
import com.savestudents.features.addMatter.ui.AddMatterContract
import com.savestudents.features.addMatter.ui.AddMatterFragment
import com.savestudents.features.addMatter.ui.AddMatterPresenter
import org.koin.dsl.module

object AddMatterDependencyInjection {
    private val accountRegisterModules = module {
        factory<AddMatterRepository> {
            AddMatterRepositoryImpl(
                client = get(),
                accountManager = get()
            )
        }

        factory { GetMatterOptionsUseCase(addMatterRepository = get()) }
        factory { CreateMatterUseCase(addMatterRepository = get()) }
        factory { CreateMatterOptionsUseCase(addMatterRepository = get()) }

        factory<AddMatterContract.Presenter> { (view: AddMatterFragment) ->
            AddMatterPresenter(
                view = view,
                createMatterUseCase = get(),
                createMatterOptionsUseCase = get(),
                getMatterOptionsUseCase = get()
            )
        }
    }

    val modules = arrayOf(accountRegisterModules)
}