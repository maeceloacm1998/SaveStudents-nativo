package com.example.savestudents.model.viewModel

interface IHomeViewModel {
    fun getSubjectList()
    fun filterSubjectList(collectionPath: String, periodList: MutableList<String>?, shift: String)
}