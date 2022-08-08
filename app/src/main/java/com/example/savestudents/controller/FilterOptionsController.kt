package com.example.savestudents.controller

import com.airbnb.epoxy.EpoxyController
import com.example.savestudents.R
import com.example.savestudents.constants.HomeConstants
import com.example.savestudents.model.FilterOption
import com.example.savestudents.model.contract.FilterOptionsContract
import com.example.savestudents.ui_component.checkbox.checkboxRadioHolder
import com.example.savestudents.ui_component.checkbox.checkboxSelectHolder
import com.example.savestudents.ui_component.separator.separatorHolder
import com.example.savestudents.ui_component.shimmer.shimmerHolder
import com.example.savestudents.ui_component.title.titleHolder

class FilterOptionsController(private val mContract: FilterOptionsContract) : EpoxyController() {
    private var offsetShiftOptions: Int = HomeConstants.Shimmer.OFFSET_SHIFT_OPTIONS
    private var offsetPeriodOptions: Int = HomeConstants.Shimmer.OFFSET_PERIOD_OPTIONS
    private val mShiftOptions: MutableList<FilterOption> = mutableListOf()
    private val mPeriodOptions: MutableList<FilterOption> = mutableListOf()
    private var mRadioSelected: String = ""
    private var mCheckboxSelectedList: MutableList<String> = mutableListOf()

    override fun buildModels() {
        handleShiftOptions()
        handlePeriodOptions()
    }

    private fun handleShiftOptions() {
        titleHolder {
            id("title")
            title("Turno")
            marginTop(24)
            marginLeft(16)
        }

        if (mShiftOptions.isEmpty()) {
            for (i in 0 until offsetShiftOptions) {
                shimmerHolder {
                    id("shimmer")
                    layout(R.layout.checkbox_radio_shimmer)
                }
            }
        } else {
            mShiftOptions.forEach { item ->
                checkboxRadioHolder {
                    id(item.id)
                    title(item.name)
                    check { mContract.clickCheckCheckboxRadioListener(it) }
                    checked(item.name == mRadioSelected)
                    marginLeft(16)
                    marginRight(5)
                }
            }
        }

        separatorHolder {
            id("separator")
            marginTop(15)
            marginRight(16)
            marginLeft(16)
        }
    }

    private fun handlePeriodOptions() {
        titleHolder {
            id("title")
            title("Periodo")
            marginTop(24)
            marginLeft(16)
        }

        if (mPeriodOptions.isEmpty()) {
            for (i in 0 until offsetPeriodOptions) {
                shimmerHolder {
                    id("shimmer")
                    layout(R.layout.checkbox_select_shimmer)
                }
            }
        } else {
            mPeriodOptions.forEach { item ->
                checkboxSelectHolder {
                    id(item.id)
                    title(item.name)
                    check { mContract.clickCheckCheckboxListener(it) }
                    uncheck { mContract.clickUncheckCheckboxListener(it) }
                    checked(handleCheckedCheckbox(item.name))
                    marginLeft(16)
                    marginRight(5)
                }
            }
        }
    }

    private fun handleCheckedCheckbox(title: String): Boolean {
        return mCheckboxSelectedList.map { it }.contains(title)
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

    fun setRadioSelected(title: String) {
        mRadioSelected = title
        requestModelBuild()
    }

    fun setCheckboxSelected(optionsSelectedList: MutableList<String>) {
        mCheckboxSelectedList = optionsSelectedList
        requestModelBuild()
    }

    fun clearFilters() {
        mCheckboxSelectedList.clear()
        mRadioSelected = ""
        requestModelBuild()
    }
}