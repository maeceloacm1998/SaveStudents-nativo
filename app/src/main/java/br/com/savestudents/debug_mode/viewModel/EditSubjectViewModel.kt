package br.com.savestudents.debug_mode.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.savestudents.debug_mode.model.repository.IEditSubjectRepository
import br.com.savestudents.debug_mode.repository.EditSubjectRepository
import br.com.savestudents.model.SubjectList
import br.com.savestudents.service.external.model.FirebaseResponseModel
import br.com.savestudents.service.external.model.OnFailureModel

class EditSubjectViewModel: ViewModel() {
    private val repository: IEditSubjectRepository = EditSubjectRepository()

    private var mSubjectItemResponse = MutableLiveData<SubjectList>()
    var subjectItemResponse: LiveData<SubjectList> = mSubjectItemResponse

    private var mUpdateSubjectItemSuccess = MutableLiveData<Boolean>()
    var updateSubjectItemSuccess: LiveData<Boolean> = mUpdateSubjectItemSuccess

    private var mUpdateSubjectItemError = MutableLiveData<OnFailureModel>()
    var updateSubjectItemError: LiveData<OnFailureModel> = mUpdateSubjectItemError

    fun getSubjectPerId(id: String) {
        repository.getSubjectPerId(id, object : FirebaseResponseModel<SubjectList> {
            override fun onSuccess(model: SubjectList) {
                mSubjectItemResponse.value = model
            }

            override fun onFailure(error: OnFailureModel) {}
        })
    }

    fun updateSubjectItem(id: String, data: SubjectList) {
        repository.updateSubjectItem(id, data, object : FirebaseResponseModel<Boolean> {
            override fun onSuccess(model: Boolean) {
                mUpdateSubjectItemSuccess.value = model
            }

            override fun onFailure(error: OnFailureModel) {
                mUpdateSubjectItemError.value = error
            }
        })
    }
}