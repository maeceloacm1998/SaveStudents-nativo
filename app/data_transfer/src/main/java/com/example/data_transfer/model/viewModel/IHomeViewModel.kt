package com.example.data_transfer.model.viewModel

interface IHomeViewModel {
    fun getSubjectList()
    fun filterSubjectList(collectionPath: String, periodList: MutableList<String>?, shift: String)
    fun searchSubjectList(collectionPath: String, searchValue: String)
}