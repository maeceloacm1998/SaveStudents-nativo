package com.example.savestudents.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.savestudents.model.CreateTimelineItem
import com.example.savestudents.model.SubjectList
import com.example.savestudents.model.TimelineItem
import com.example.savestudents.model.repository.ICreateTimelineRepository
import com.example.savestudents.model.viewModel.ICreateTimelineViewModel
import com.example.savestudents.repository.CreateTimelineRepository
import com.example.savestudents.service.internal.dao.CreateTimelineDAO
import com.example.savestudents.service.internal.database.CreateTimelineItemsDB
import com.example.savestudents.service.internal.entity.asDomainModel

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