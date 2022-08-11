package com.example.savestudents.controller

import com.airbnb.epoxy.EpoxyController
import com.example.savestudents.holder.homeHorizontalCardHolder
import com.example.savestudents.model.SubjectList
import com.example.savestudents.ui_component.title.titleHolder

class HomeActivityController : EpoxyController() {
    private var mSubjectList: MutableList<SubjectList> = mutableListOf()

    init {
        requestModelBuild()
    }

    override fun buildModels() {
        handleHomeList()
    }

    private fun handleHomeList() {
        titleHolder {
            id("title_list")
            title("Sugestões de matérias")
            marginBottom(8)
            marginLeft(16)
        }

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

    fun setSubjectList(subjectList: List<SubjectList>) {
        subjectList.forEach { item ->
            mSubjectList.add(item)
        }
        requestModelBuild()
    }

}