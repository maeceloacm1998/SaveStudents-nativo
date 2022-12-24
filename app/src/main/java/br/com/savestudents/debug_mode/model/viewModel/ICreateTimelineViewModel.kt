package br.com.savestudents.debug_mode.model.viewModel

import br.com.savestudents.model.SubjectList
import br.com.savestudents.model.TimelineItem

interface ICreateTimelineViewModel {
    fun getTimelineItems()
    fun deleteTimelineItem(id: Int)
    fun clearTimelineItemList()
    fun createDocument(collectionPath: String): String
    fun createSubject(collectionPath: String, documentPath: String, data: SubjectList)
    fun createTimeline(collectionPath: String, documentPath: String, data: TimelineItem)
}