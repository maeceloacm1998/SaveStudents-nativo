package br.oficial.savestudents.controller

import com.airbnb.epoxy.EpoxyController
import br.oficial.savestudents.ui_component.header.headerHolder
import br.oficial.savestudents.ui_component.search.searchEditTextHolder
import com.example.data_transfer.model.contract.HeaderHomeActivityContract

class HeaderHomeActivityController(private val mContract: HeaderHomeActivityContract) : EpoxyController() {
    private var adminModeActive: Boolean = false

    override fun buildModels() {
            handleHeader()
            handleSearchEditText()
    }

    private fun handleHeader() {
        headerHolder {
            id("header")
            adminModeOn(adminModeActive)
            adminModeOnActiveListener(mContract::adminModeOnActiveListener)
            joinAdminModeListener(mContract::joinAdminModeListener)
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

    fun isAdminMode(isAdminModeActive: Boolean) {
        adminModeActive = isAdminModeActive
        requestModelBuild()
    }
}