package com.savestudents.features.addMatter.ui

import android.os.Build
import androidx.annotation.RequiresApi
import com.savestudents.components.snackbar.SnackBarCustomType
import com.savestudents.features.addMatter.domain.CreateMatterOptionsUseCase
import com.savestudents.features.addMatter.domain.CreateMatterUseCase
import com.savestudents.features.addMatter.domain.GetMatterOptionsUseCase
import com.savestudents.features.addMatter.models.Matter

class AddMatterPresenter(
    val view: AddMatterContract.View,
    private val createMatterUseCase: CreateMatterUseCase,
    private val getMatterOptionsUseCase: GetMatterOptionsUseCase,
    private val createMatterOptionsUseCase: CreateMatterOptionsUseCase
) : AddMatterContract.Presenter {
    private var matterList: List<Matter> = mutableListOf()
    private var matterSelected: Matter? = null
    private var initialTime: String? = null

    override fun start() {
        view.run {
            onSetupViewsTextLayout()
            onSetupViewsAddMatter()
            onSetupViewsCreateMatter()
            onSetupViewsMatterInput()
            onLoading(true)
        }
    }

    override suspend fun handleFetchMatters() {
        getMatterOptionsUseCase()
            .onSuccess { matterOptions ->
                matterList = matterOptions
                view.onSetMatterOptions(matterOptions.map { matter -> matter.matterName })
                view.onLoading(false)
            }
            .onFailure {
                view.showError()
            }
    }

    override fun onMatterSelect(option: String) {
        val matter = checkNotNull(matterList.find { matter -> matter.matterName == option })
        view.onMatterSelect(matter)
        matterSelected = matter
    }

    override fun onSaveInitialHourSelected(time: String) {
        initialTime = time
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun handleValidateMatter(daysSelected: List<String>) {
        when {
            matterSelected == null -> view.showErrorMatterNotSelected(true)
            daysSelected.isEmpty() -> view.showErrorDaysNotSelected(true)
            initialTime == null -> view.showErrorInitialHourNotSelected(true)
            else -> {
                view.run {
                    showErrorMatterNotSelected(false)
                    showErrorDaysNotSelected(false)
                    showErrorInitialHourNotSelected(false)
                }
                registerMatter(daysSelected)
            }
        }
        view.onLoadingRegister(false)
    }

    override suspend fun handleValidateAddMatterOption(matterName: String, period: String) {
        when {
            matterName.isEmpty() -> view.showErrorAddMatterOptionMatterNameNotSelected(true)
            period.isEmpty() -> view.showErrorAddMatterOptionPeriodNotSelected(true)
            else -> {
                view.run {
                    showErrorAddMatterOptionMatterNameNotSelected(false)
                    showErrorAddMatterOptionPeriodNotSelected(false)
                }
                registerMatterOption(matterName, period)
            }
        }
    }

    private suspend fun registerMatterOption(matterName: String, period: String) {
        createMatterOptionsUseCase(
            period = period,
            matterName = matterName
        ).onSuccess {
            view.showSnackbarAddMatterOption(
                MESSAGE_SUCCESS_ADD_MATTER_OPTION,
                SnackBarCustomType.SUCCESS
            )
        }.onFailure {
            view.showSnackbarAddMatterOption(
                MESSAGE_ERROR_ADD_MATTER_OPTION,
                SnackBarCustomType.ERROR
            )
        }
    }

    override fun onGetInitialHour(): Int {
        return initialTime?.split(":")?.get(0)?.toInt() ?: 10
    }

    override fun onGetInitialMinutes(): Int {
        return initialTime?.split(":")?.get(1)?.toInt() ?: 0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun registerMatter(daysSelected: List<String>) {
        createMatterUseCase(
            matter = checkNotNull(matterSelected),
            initialTime = checkNotNull(initialTime),
            daysOfWeekSelected = daysSelected
        ).onSuccess {
            view.showSnackbarStatus(
                matterName = matterSelected?.matterName,
                snackBarCustomType = SnackBarCustomType.SUCCESS
            )
            view.goToCurriculum()
        }.onFailure {
            view.showSnackbarStatus(
                matterName = matterSelected?.matterName,
                snackBarCustomType = SnackBarCustomType.ERROR
            )
            view.onLoadingRegister(false)
        }
    }

    companion object {
        private const val MESSAGE_SUCCESS_ADD_MATTER_OPTION = "Sucesso ao cadastrar a matéria"
        private const val MESSAGE_ERROR_ADD_MATTER_OPTION = "Erro ao cadastrar a matéria"
    }
}