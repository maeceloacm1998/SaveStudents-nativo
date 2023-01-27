package br.oficial.savestudents.debug_mode.controller

import br.oficial.savestudents.R
import br.oficial.savestudents.debug_mode.model.contract.SelectTimelineTypeContract
import br.oficial.savestudents.ui_component.inputChips.inputChipsHolder
import br.oficial.savestudents.ui_component.shimmer.shimmerHolder
import com.airbnb.epoxy.EpoxyController
import com.example.data_transfer.model.TimelineType

class SelectTimelineTypeController(private val contract: SelectTimelineTypeContract) :
    EpoxyController() {
    private var mTimelineTypesList: MutableList<TimelineType> = mutableListOf()
    private var timelineTypeSelected: String = "Matéria"

    override fun buildModels() {
        if (mTimelineTypesList.isEmpty()) {
            shimmerHolder {
                id("input_chips_shimmer")
                layout(R.layout.input_chips_shimmer)
            }
        } else {
            mTimelineTypesList.forEach { item ->
                inputChipsHolder {
                    id(item.id)
                    title(item.type)
                    selected(isSelectedItem(item.type))
                    clickTimelineTypeListener(contract::clickTimelineTypeListener)
                    marginRight(10)
                }
            }
        }
    }

    private fun isSelectedItem(type: String?): Boolean {
        return timelineTypeSelected == type
    }

    fun setTimelineTypesList(timelineTypesList: List<TimelineType>) {
        mTimelineTypesList = timelineTypesList.toMutableList()
        requestModelBuild()
    }

    fun setTimelineTypesSelected(typeSelected: String) {
        timelineTypeSelected = typeSelected
        requestModelBuild()
    }
}