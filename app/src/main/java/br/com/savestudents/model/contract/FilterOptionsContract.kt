package br.com.savestudents.model.contract

interface FilterOptionsContract {
    fun clickCheckCheckboxListener(title: String)
    fun clickUncheckCheckboxListener(title: String)
    fun clickCheckCheckboxRadioListener(title: String)
    fun tryAgainListener()
}