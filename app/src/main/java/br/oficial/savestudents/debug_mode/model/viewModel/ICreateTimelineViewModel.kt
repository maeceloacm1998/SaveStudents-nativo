package br.oficial.savestudents.debug_mode.model.viewModel

import br.oficial.savestudents.model.SubjectList
import br.oficial.savestudents.model.TimelineItem

interface ICreateTimelineViewModel {
    fun getTimelineItems()
    fun getTimelineTypes()
    fun deleteTimelineItem(id: Int)
    fun clearTimelineItemList()
    fun createDocument(collectionPath: String): String
    fun createSubject(collectionPath: String, documentPath: String, data: SubjectList)
    fun createTimeline(collectionPath: String, documentPath: String, data: TimelineItem)
}