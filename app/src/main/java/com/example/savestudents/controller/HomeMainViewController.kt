package com.example.savestudents.controller

import com.airbnb.epoxy.EpoxyController
import com.example.savestudents.holder.homeHorizontalCardHolder
import com.example.savestudents.ui_component.Title.titleHolder
import com.example.savestudents.ui_component.header.headerHolder
import com.example.savestudents.ui_component.search.searchEditTextHolder

class HomeMainViewController: EpoxyController() {
    init {
        requestModelBuild()
    }

    override fun buildModels() {
        handleHomeList()
    }

    private fun handleHomeList() {
        titleHolder {
            id("title_list")
            title("Sugestões de matérias")
            marginBottom(8)
            marginLeft(16)
        }
        var i = 0

        do {
            homeHorizontalCardHolder {
                id("card")
                title("Algoritmos em Grafos")
                subtitle("4º Período")
                marginLeft(16)
                marginRight(16)
                marginBottom(8)
            }
            i++
        }while (i <= 10)
    }
    
}