package br.oficial.savestudents.debug_mode.model.contract

interface SearchBarContract {
    fun clickFilterButtonListener()
    fun clickSearchBarListener()
    fun clickButtonCancelListener()
    fun editTextValue(text: String)
}