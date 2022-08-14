package com.example.savestudents.controller

import com.airbnb.epoxy.EpoxyController
import com.example.savestudents.model.contract.HeaderHomeActivityContract
import com.example.savestudents.ui_component.header.headerHolder
import com.example.savestudents.ui_component.search.searchEditTextHolder

class HeaderHomeActivityController(private val mContract: HeaderHomeActivityContract) : EpoxyController() {

    override fun buildModels() {
            handleHeader()
            handleSearchEditText()
    }

    private fun handleHeader() {
        headerHolder {
            id("header")
        }
    }

    private fun handleSearchEditText() {
        searchEditTextHolder {
            id("searchEditText")
            clickFilterButtonListener(mContract::clickFilterButtonListener)
            clickSearchBarListener(mContract::clickSearchBarListener)
            clickButtonCancelListener(mContract::clickButtonCancelListener)
            editTextValue(mContract::editTextValue)
        }
    }
}