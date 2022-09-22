package br.com.savestudents.controller

import com.airbnb.epoxy.EpoxyController
import br.com.savestudents.model.contract.HeaderHomeActivityContract
import br.com.savestudents.ui_component.header.headerHolder
import br.com.savestudents.ui_component.search.searchEditTextHolder

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