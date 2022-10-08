package br.com.savestudents.debug_mode.controller

import br.com.savestudents.R
import br.com.savestudents.debug_mode.holder.subjectEditHorizontalCardHolder
import br.com.savestudents.debug_mode.model.contract.SubjectListContract
import br.com.savestudents.holder.responseErrorHolder
import br.com.savestudents.model.SubjectList
import br.com.savestudents.model.error.SubjectListErrorModel
import br.com.savestudents.ui_component.shimmer.shimmerHolder
import com.airbnb.epoxy.EpoxyController

class SubjectListController(private val mContract: SubjectListContract) : EpoxyController() {
    private var subjectList: MutableList<SubjectList> = mutableListOf()
    private var loading: Boolean = false
    private var responseError: SubjectListErrorModel? = null

    override fun buildModels() {
        if(responseError != null) {
            responseErrorHolder {
                id("error_holder")
                message(responseError?.message)
                description(responseError?.description)
            }
        } else {
            handleSubjectEditCardHorizontal()
        }
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
        requestModelBuild()
    }

    fun setLoading(loading: Boolean) {
        this.loading = loading
        requestModelBuild()
    }

    fun setResponseError(error: SubjectListErrorModel?) {
        this.responseError = error
        requestModelBuild()
    }
}