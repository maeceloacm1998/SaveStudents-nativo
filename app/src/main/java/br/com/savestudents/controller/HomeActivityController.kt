package br.com.savestudents.controller

import com.airbnb.epoxy.EpoxyController
import br.com.savestudents.R
import br.com.savestudents.constants.HomeConstants
import br.com.savestudents.holder.homeHorizontalCardHolder
import br.com.savestudents.holder.responseErrorHolder
import br.com.savestudents.holder.responseErrorTryAgainListenerHolder
import br.com.savestudents.model.SubjectList
import br.com.savestudents.model.contract.HomeActivityContract
import br.com.savestudents.model.error.SubjectListErrorModel
import br.com.savestudents.ui_component.shimmer.shimmerHolder
import br.com.savestudents.ui_component.title.titleHolder

class HomeActivityController(private val mContract: HomeActivityContract) : EpoxyController() {
    private var mSubjectList: MutableList<SubjectList> = mutableListOf()
    private var isResponseError: Boolean = false
    private lateinit var mResponseError: SubjectListErrorModel

    override fun buildModels() {
        if (isResponseError) {
            handleResponseError()
        } else {
            handleHomeList()
        }
    }

    private fun handleHomeList() {
        titleHolder {
            id("title_list")
            title("Sugestões de matérias")
            marginBottom(8)
            marginLeft(16)
        }

        if (mSubjectList.isEmpty()) {
            shimmerHolder {
                id("shimmer")
                layout(R.layout.home_horizontal_card_shimmer)
                marginLeft(16)
                marginRight(16)
            }
        } else {
            mSubjectList.forEach { item ->
                homeHorizontalCardHolder {
                    id(item.id)
                    subjectId(item.id)
                    title(item.subjectName)
                    period(item.period)
                    clickHorizontalCardListener(mContract::clickHorizontalCardListener)
                    marginLeft(16)
                    marginRight(16)
                    marginBottom(8)
                }
            }
        }
    }

    private fun handleResponseError() {
        if (mResponseError.type == HomeConstants.Filter.TYPE_FILTER_ERROR) {
            responseErrorHolder {
                id(HomeConstants.Filter.TYPE_FILTER_ERROR)
                message(mResponseError.message)
                description(mResponseError.description)
            }
        } else {
            responseErrorTryAgainListenerHolder {
                id(HomeConstants.Filter.TYPE_LIST_ERROR)
                message(mResponseError.message)
                tryAgainListener(mContract::tryAgainListener)
            }
        }
    }

    fun setSubjectList(subjectList: List<SubjectList>) {
        subjectList.forEach { item ->
            mSubjectList.add(item)
        }
        requestModelBuild()
    }

    fun clearSubjectList() {
        mSubjectList.clear()
        requestModelBuild()
    }

    fun isResponseError(error: Boolean) {
        isResponseError = error
    }

    fun setResponseError(responseError: SubjectListErrorModel) {
        mResponseError = responseError
        requestModelBuild()
    }
}