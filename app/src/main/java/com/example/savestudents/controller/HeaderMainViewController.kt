package com.example.savestudents.controller

import com.airbnb.epoxy.EpoxyController
import com.example.savestudents.ui_component.header.headerHolder
import com.example.savestudents.ui_component.search.searchEditTextHolder

class HeaderMainViewController : EpoxyController() {
    init {
        requestModelBuild()
    }

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
        }
    }
}