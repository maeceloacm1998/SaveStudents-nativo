package br.com.savestudents.controller

import com.airbnb.epoxy.EpoxyController
import br.com.savestudents.R
import br.com.savestudents.model.FilterOption
import br.com.savestudents.model.contract.SelectOptionsContract
import br.com.savestudents.ui_component.checkbox.checkboxRadioHolder
import br.com.savestudents.ui_component.shimmer.shimmerHolder

class SelectOptionsController(private val contract: SelectOptionsContract) : EpoxyController() {
    private var loading: Boolean = true
    private var optionsList: List<FilterOption> = mutableListOf()

    override fun buildModels() {
        if (loading) {
            shimmerHolder {
                id("checkbox_shimmer")
                layout(R.layout.checkbox_radio_shimmer)
                marginLeft(16)
                marginRight(5)
            }
        } else {
            optionsList.forEach { item ->
                checkboxRadioHolder {
                    id(item.id)
                    title(item.name)
                    check(contract::clickedCheckboxListener)
                    marginLeft(16)
                    marginRight(5)
                }
            }
        }
    }

    fun setOptionsList(data: List<FilterOption>) {
        optionsList = data
        requestModelBuild()
    }

    fun setLoading(loading: Boolean) {
        this.loading = loading
        requestModelBuild()
    }
}