package com.example.data_transfer.model.viewModel

interface IFilterOptionsViewModel {
    fun getShiftOptions(collectionPath: String, orderByName: String)
    fun getPeriodOptions(collectionPath: String, orderByName: String)
}