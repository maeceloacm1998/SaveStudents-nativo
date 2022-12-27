package br.oficial.savestudents.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.oficial.savestudents.constants.FirestoreDbConstants
import br.oficial.savestudents.dto.FilterOptionDTO
import br.oficial.savestudents.dto.asDomainModel
import br.oficial.savestudents.model.FilterOption
import br.oficial.savestudents.model.repository.IFilterOptionsRepository
import br.oficial.savestudents.model.viewModel.IFilterOptionsViewModel
import br.oficial.savestudents.repository.FilterOptionsRepository
import br.oficial.savestudents.service.external.model.FirebaseResponseModel
import br.oficial.savestudents.service.external.model.OnFailureModel

class FilterOptionsViewModel : ViewModel(), IFilterOptionsViewModel {
    private val repository: IFilterOptionsRepository = FilterOptionsRepository()

    private val mShiftOptions = MutableLiveData<List<FilterOption>>()
    val shiftOptions: LiveData<List<FilterOption>> = mShiftOptions

    private val mPeriodOptions = MutableLiveData<List<FilterOption>>()
    val periodOptions: LiveData<List<FilterOption>> = mPeriodOptions

    private val mHandleResponseError = MutableLiveData<Boolean>()
    val handleResponseError: LiveData<Boolean> = mHandleResponseError

    override fun getShiftOptions(collectionPath: String, orderByName: String) {
        repository.getFilterOptions(
            collectionPath,
            orderByName,
            object : FirebaseResponseModel<List<FilterOptionDTO>> {
                override fun onSuccess(model: List<FilterOptionDTO>) {
                    mShiftOptions.value = model.asDomainModel()
                    mHandleResponseError.value = false
                }

                override fun onFailure(error: OnFailureModel) {
                    handleError(error.code)
                }
            })
    }

    override fun getPeriodOptions(collectionPath: String, orderByName: String) {
        repository.getFilterOptions(
            collectionPath,
            orderByName,
            object : FirebaseResponseModel<List<FilterOptionDTO>> {
                override fun onSuccess(model: List<FilterOptionDTO>) {
                    mPeriodOptions.value = model.asDomainModel()
                }

                override fun onFailure(error: OnFailureModel) {
                    handleError(error.code)
                }
            })
    }

    private fun handleError(code: Int) {
        when (code) {
            FirestoreDbConstants.StatusCode.NOT_FOUND ->
                mHandleResponseError.value = true
        }
    }
}