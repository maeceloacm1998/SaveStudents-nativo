package br.com.savestudents.debug_mode.controller

import br.com.savestudents.debug_mode.model.contract.SearchBarContract
import br.com.savestudents.ui_component.header.headerHolder
import br.com.savestudents.ui_component.search.searchEditTextHolder
import com.airbnb.epoxy.EpoxyController

class SearchBarDebugModeController(private val mContract: SearchBarContract) : EpoxyController() {
    override fun buildModels() {
        headerHolder {
            id("header_debug_component")
        }

        searchEditTextHolder {
            id("search_bar_component")
            clickButtonCancelListener(mContract::clickButtonCancelListener)
            clickFilterButtonListener(mContract::clickFilterButtonListener)
            clickSearchBarListener(mContract::clickSearchBarListener)
            editTextValue(mContract::editTextValue)
        }
    }
}