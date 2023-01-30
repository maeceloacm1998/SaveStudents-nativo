package br.oficial.savestudents.debug_mode.model.viewModel

import com.example.data_transfer.model.SubjectList
import com.example.data_transfer.model.TimelineItem


interface ICreateTimelineViewModel {
    fun getTimelineItems()
    fun getTimelineTypes()
    fun deleteTimelineItem(id: Int)
    fun clearTimelineItemList()
    fun createDocument(collectionPath: String): String
    fun createSubject(collectionPath: String, documentPath: String, data: SubjectList)
    fun createTimeline(collectionPath: String, documentPath: String, data: TimelineItem)
}