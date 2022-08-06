package com.example.savestudents.controller

import com.airbnb.epoxy.EpoxyController
import com.example.savestudents.model.FilterOption
import com.example.savestudents.ui_component.Title.titleHolder

class FilterOptionsController : EpoxyController(){
    private val mShiftOptions: MutableList<FilterOption> = mutableListOf()
    private val mPeriodOptions: MutableList<FilterOption> = mutableListOf()

    override fun buildModels() {
        handleShiftOptions()
        handlePeriodOptions()
    }

    private fun handleShiftOptions() {
        titleHolder {
            id("title")
            title("Turno")
            marginLeft(16)
        }
    }

    private fun handlePeriodOptions() {
        titleHolder {
            id("title")
            title("Periodo")
            marginLeft(16)
        }
    }

    fun setShiftOptions(options: List<FilterOption>) {
        options.forEach { item ->
            mShiftOptions.add(item)
        }
        requestModelBuild()
    }

    fun setPeriodOptions(options: List<FilterOption>) {
        options.forEach { item ->
            mPeriodOptions.add(item)
        }
        requestModelBuild()
    }
}