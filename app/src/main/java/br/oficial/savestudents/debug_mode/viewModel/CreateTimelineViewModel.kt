package br.oficial.savestudents.debug_mode.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.oficial.savestudents.debug_mode.model.repository.ICreateTimelineRepository
import br.oficial.savestudents.debug_mode.model.viewModel.ICreateTimelineViewModel
import br.oficial.savestudents.debug_mode.repository.CreateTimelineRepository
import com.br.core.service.internal.dao.CreateTimelineDAO
import com.br.core.service.internal.database.CreateTimelineItemsDB
import com.example.data_transfer.model.*
import com.example.data_transfer.model.entity.asDomainModel
import com.example.data_transfer.model.repository.FirebaseResponseModel

class CreateTimelineViewModel(mContext: Context) : ViewModel(), ICreateTimelineViewModel {
    private var timelineItemDAO: CreateTimelineDAO =
        CreateTimelineItemsDB.getDataBase(mContext).createTimelineDAO()

    private val repository: ICreateTimelineRepository = CreateTimelineRepository()

    private var mTimelineItems = MutableLiveData<MutableList<CreateTimelineItem>>()
    val timelineItems: LiveData<MutableList<CreateTimelineItem>> = mTimelineItems

    private var mTimelineTypes = MutableLiveData<List<TimelineType>>()
    val timelineTypes: LiveData<List<TimelineType>> = mTimelineTypes

    override fun getTimelineItems() {
        mTimelineItems.value = timelineItemDAO.getTimelineItems().asDomainModel().toMutableList()
    }

    override fun getTimelineTypes() {
        repository.getTimelineTypes(object : FirebaseResponseModel<List<TimelineType>> {
            override fun onSuccess(model: List<TimelineType>) {
                mTimelineTypes.value = model
            }

            override fun onFailure(error: OnFailureModel) {}
        })
    }

    override fun deleteTimelineItem(id: Int) {
        timelineItemDAO.deleteTimelineItem(id)
    }

    override fun clearTimelineItemList() {
        timelineItemDAO.clearTimelineItemList()
    }

    override fun createDocument(collectionPath: String): String {
        return repository.createDocument(collectionPath)
    }

    override fun createSubject(collectionPath: String, documentPath: String, data: SubjectList) {
        repository.setDocumentData(collectionPath, documentPath, data)
    }

    override fun createTimeline(collectionPath: String, documentPath: String, data: TimelineItem) {
        repository.setDocumentData(collectionPath, documentPath, data)
    }
}