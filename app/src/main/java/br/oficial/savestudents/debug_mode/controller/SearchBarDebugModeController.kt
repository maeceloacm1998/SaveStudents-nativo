package br.oficial.savestudents.debug_mode.controller

import br.oficial.savestudents.debug_mode.model.contract.SearchBarContract
import br.oficial.savestudents.ui_component.search.searchEditTextHolder
import com.airbnb.epoxy.EpoxyController

class SearchBarDebugModeController(private val mContract: SearchBarContract) : EpoxyController() {
    override fun buildModels() {
        searchEditTextHolder {
            id("search_bar_component")
            clickButtonCancelListener(mContract::clickButtonCancelListener)
            clickFilterButtonListener(mContract::clickFilterButtonListener)
            clickSearchBarListener(mContract::clickSearchBarListener)
            editTextValue(mContract::editTextValue)
        }
    }
}