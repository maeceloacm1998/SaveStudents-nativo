package br.oficial.savestudents.debug_mode.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.oficial.savestudents.debug_mode.model.repository.ICreateTimelineRepository
import br.oficial.savestudents.debug_mode.model.viewModel.ICreateTimelineViewModel
import br.oficial.savestudents.debug_mode.repository.CreateTimelineRepository
import br.oficial.savestudents.model.CreateTimelineItem
import br.oficial.savestudents.model.SubjectList
import br.oficial.savestudents.model.TimelineItem
import br.oficial.savestudents.model.TimelineType
import br.oficial.savestudents.service.external.model.FirebaseResponseModel
import br.oficial.savestudents.service.external.model.OnFailureModel
import br.oficial.savestudents.service.internal.dao.CreateTimelineDAO
import br.oficial.savestudents.service.internal.database.CreateTimelineItemsDB
import br.oficial.savestudents.service.internal.entity.asDomainModel

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