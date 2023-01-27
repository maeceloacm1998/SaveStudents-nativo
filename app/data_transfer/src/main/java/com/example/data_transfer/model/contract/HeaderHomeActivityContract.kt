package com.example.data_transfer.model.contract

interface HeaderHomeActivityContract {
    fun clickFilterButtonListener()
    fun clickSearchBarListener()
    fun clickButtonCancelListener()
    fun editTextValue(text: String)
    fun adminModeOnActiveListener()
    fun joinAdminModeListener()
}