package com.example.savestudents.model.viewModel

import com.example.savestudents.model.SubjectList
import com.example.savestudents.model.TimelineItem

interface ICreateTimelineViewModel {
    fun getTimelineItems()
    fun deleteTimelineItem(id: Int)
    fun clearTimelineItemList()
    fun createDocument(collectionPath: String): String
    fun createSubject(collectionPath: String, documentPath: String, data: SubjectList)
    fun createTimeline(collectionPath: String, documentPath: String, data: TimelineItem)
}