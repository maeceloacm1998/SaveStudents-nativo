package com.example.savestudents.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.savestudents.model.CreateTimelineItem
import com.example.savestudents.model.viewModel.ICreateTimelineViewModel
import com.example.savestudents.service.internal.dao.CreateTimelineDAO
import com.example.savestudents.service.internal.database.CreateTimelineItemsDB
import com.example.savestudents.service.internal.entity.asDomainModel

class CreateTimelineViewModel(mContext: Context) : ViewModel(), ICreateTimelineViewModel {
    private var timelineItemDAO: CreateTimelineDAO =
         CreateTimelineItemsDB.getDataBase(mContext).createTimelineDAO()

    private var mTimelineItems = MutableLiveData<MutableList<CreateTimelineItem>>()
    val timelineItems: LiveData<MutableList<CreateTimelineItem>> = mTimelineItems

    override fun getTimelineItems() {
       mTimelineItems.value = timelineItemDAO.getTimelineItems().asDomainModel().toMutableList()
    }
}