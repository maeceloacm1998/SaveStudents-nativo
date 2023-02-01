package br.oficial.savestudents.controller

import br.oficial.savestudents.R
import br.oficial.savestudents.holder.informationHolder
import br.oficial.savestudents.holder.subtitleTimelineListHolder
import br.oficial.savestudents.ui_component.separator.separatorHolder
import br.oficial.savestudents.ui_component.shimmer.shimmerHolder
import com.airbnb.epoxy.EpoxyController
import com.example.data_transfer.model.TimelineItem

class InformationTimelineController: EpoxyController() {
    private var timelineList: TimelineItem = TimelineItem()
    private var isLoading = false

    override fun buildModels() {
        handleInformationHolder()
        handleTitleTimelineList()
    }

    private fun handleInformationHolder() {
        if (isLoading) {
            shimmerHolder {
                id("information_shimmer")
                layout(R.layout.information_timeline_shimmer)
                marginTop(10)
                marginRight(24)
                marginLeft(24)
            }
        } else {
            if(timelineList.subjectsInformation != null) {
                informationHolder {
                    id(timelineList.subjectsInformation?.id)
                    title(timelineList.subjectsInformation?.subjectName)
                    period(timelineList.subjectsInformation?.period)
                    shift(timelineList.subjectsInformation?.shift)
                    teacher(timelineList.subjectsInformation?.teacherName)
                    subjectModel(timelineList.subjectsInformation?.subjectModel)
                    marginTop(10)
                    marginRight(24)
                    marginLeft(24)
                }
            }
        }

        separatorHolder {
            id("separator")
            marginTop(24)
            marginLeft(16)
            marginRight(16)
        }
    }

    private fun handleTitleTimelineList() {
        subtitleTimelineListHolder {
            id("subtitle_timeline_list")
            marginLeft(16)
            marginRight(16)
            marginTop(16)
        }
    }

    fun setLoading(status: Boolean) {
        isLoading = status
        requestModelBuild()
    }

    fun setTimelineList(timelineList: TimelineItem) {
        this.timelineList = timelineList
        requestModelBuild()
    }
}