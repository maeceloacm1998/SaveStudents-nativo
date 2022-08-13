package com.example.savestudents.model.viewModel

interface IHomeViewModel {
    fun getSubjectList()
    fun filterSubjectListWithShift(collectionPath: String, field: String, values: MutableList<String>)
    fun filterSubjectListWithPeriod(collectionPath: String, field: String, values: MutableList<String>?)
    fun filterSubjectListWithPeriodAndShift(collectionPath: String, field: String, values: MutableList<String>?, checkboxRadioSelected: String)
}