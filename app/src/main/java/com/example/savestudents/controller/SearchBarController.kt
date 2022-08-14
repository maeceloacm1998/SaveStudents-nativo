package com.example.savestudents.controller

import com.airbnb.epoxy.EpoxyController
import com.example.savestudents.R
import com.example.savestudents.holder.homeHorizontalCardHolder
import com.example.savestudents.model.SubjectList
import com.example.savestudents.ui_component.shimmer.shimmerHolder
import com.example.savestudents.ui_component.title.titleHolder

class SearchBarController: EpoxyController() {
    private var mSubjectList: MutableList<SubjectList> = mutableListOf()

    override fun buildModels() {
        handleSearchList()
    }

    private fun handleSearchList() {
        titleHolder {
            id("title_holder")
            title("Você está buscando por")
            marginBottom(8)
            marginLeft(16)
        }

        if(mSubjectList.isEmpty()){
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
                    title(item.title)
                    item.period?.let { period(it) }
                    marginLeft(16)
                    marginRight(16)
                    marginBottom(8)
                }
            }
        }
    }

    fun setSubjectList(list: List<SubjectList>) {
        list.forEach { item ->
            mSubjectList.add(item)
        }
        requestModelBuild()
    }
}