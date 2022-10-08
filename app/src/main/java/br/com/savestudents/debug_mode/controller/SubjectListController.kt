package br.com.savestudents.debug_mode.controller

import br.com.savestudents.R
import br.com.savestudents.debug_mode.holder.subjectEditHorizontalCardHolder
import br.com.savestudents.debug_mode.model.contract.SubjectListContract
import br.com.savestudents.model.SubjectList
import br.com.savestudents.ui_component.shimmer.shimmerHolder
import com.airbnb.epoxy.EpoxyController

class SubjectListController(private val mContract: SubjectListContract) : EpoxyController() {
    private var subjectList: MutableList<SubjectList> = mutableListOf()
    private var loading: Boolean = true

    override fun buildModels() {
        handleSubjectEditCardHorizontal()
    }

    private fun handleSubjectEditCardHorizontal() {
        if (loading) {
            shimmerHolder {
                id("shimmer_holder")
                layout(R.layout.subject_edit_horizontal_card_shimmer)
                marginLeft(16)
                marginRight(16)
                marginTop(8)
            }
        } else {
            subjectList.forEach { subjectItem ->
                subjectEditHorizontalCardHolder {
                    id(subjectItem.id)
                    mId(subjectItem.id)
                    title(subjectItem.subjectName)
                    subTitle(subjectItem.period)
                    clickEditOptionListener(mContract::clickEditOptionListener)
                    clickDeleteSubjectListener(mContract::clickDeleteSubjectListener)
                    marginLeft(16)
                    marginRight(16)
                    marginTop(8)
                }
            }
        }
    }

    fun setSubjectList(subjectList: MutableList<SubjectList>) {
        this.subjectList = subjectList
    }

    fun setLoading(loading: Boolean) {
        this.loading = loading
    }
}