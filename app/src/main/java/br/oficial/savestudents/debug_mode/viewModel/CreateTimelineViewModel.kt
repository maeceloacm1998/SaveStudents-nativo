package br.oficial.savestudents.debug_mode.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.oficial.savestudents.model.CreateTimelineItem
import br.oficial.savestudents.model.SubjectList
import br.oficial.savestudents.model.TimelineItem
import br.oficial.savestudents.model.repository.ICreateTimelineRepository
import br.oficial.savestudents.debug_mode.model.viewModel.ICreateTimelineViewModel
import br.oficial.savestudents.repository.CreateTimelineRepository
import br.oficial.savestudents.service.internal.dao.CreateTimelineDAO
import br.oficial.savestudents.service.internal.database.CreateTimelineItemsDB
import br.oficial.savestudents.service.internal.entity.asDomainModel

class CreateTimelineViewModel(mContext: Context) : ViewModel(), ICreateTimelineViewModel {
    private var timelineItemDAO: CreateTimelineDAO =
        CreateTimelineItemsDB.getDataBase(mContext).createTimelineDAO()

    private val repository: ICreateTimelineRepository = CreateTimelineRepository()

    private var mTimelineItems = MutableLiveData<MutableList<CreateTimelineItem>>()
    val timelineItems: LiveData<MutableList<CreateTimelineItem>> = mTimelineItems

    override fun getTimelineItems() {
        mTimelineItems.value = timelineItemDAO.getTimelineItems().asDomainModel().toMutableList()
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