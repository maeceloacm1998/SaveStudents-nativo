package com.savestudents.features.home.data.di

import com.savestudents.features.home.data.HomeRepository
import com.savestudents.features.home.data.HomeRepositoryImpl
import com.savestudents.features.home.domain.GetScheduleUseCase
import com.savestudents.features.home.ui.HomeContract
import com.savestudents.features.home.ui.HomeFragment
import com.savestudents.features.home.ui.HomePresenter
import org.koin.dsl.module

object HomeDependencyInjection {
    private val HomeModules = module {
        factory<HomeRepository> {
            HomeRepositoryImpl(
                client = get(),
                accountManager = get()
            )
        }

        factory {
            GetScheduleUseCase(
                homeRepository = get()
            )
        }

        factory<HomeContract.Presenter> { (view: HomeFragment) ->
            HomePresenter(
                view = view,
                getScheduleUseCase = get()
            )
        }
    }

    val modules = arrayOf(HomeModules)
}