package com.example.savestudents.controller

import com.airbnb.epoxy.EpoxyController
import com.example.savestudents.R
import com.example.savestudents.constants.HomeConstants
import com.example.savestudents.holder.homeHorizontalCardHolder
import com.example.savestudents.holder.responseErrorHolder
import com.example.savestudents.model.SubjectList
import com.example.savestudents.model.error.SubjectListErrorModel
import com.example.savestudents.ui_component.shimmer.shimmerHolder
import com.example.savestudents.ui_component.title.titleHolder

class SearchBarController : EpoxyController() {
    private var mSubjectList: MutableList<SubjectList> = mutableListOf()
    private lateinit var mResponseError: SubjectListErrorModel
    private var isResponseError: Boolean = false
    private var loading: Boolean = false

    override fun buildModels() {
        if (isResponseError) {
            handleResponseError()
        } else {
            handleSearchList()
        }
    }

    private fun handleSearchList() {
        titleHolder {
            id("title_holder")
            title("Você está buscando por")
            marginBottom(8)
            marginLeft(16)
        }

        if (loading) {
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
                    title(item.subjectName)
                    item.period?.let { period(it) }
                    marginLeft(16)
                    marginRight(16)
                    marginBottom(8)
                }
            }
        }
    }

    private fun handleResponseError() {
        responseErrorHolder {
            id(HomeConstants.Filter.TYPE_SEARCH_ERROR)
            message(mResponseError.message)
            description(mResponseError.description)
        }
    }

    fun setSubjectList(list: List<SubjectList>) {
        mSubjectList = list.toMutableList()
        requestModelBuild()
    }

    fun setLoading(status: Boolean) {
        loading = status
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