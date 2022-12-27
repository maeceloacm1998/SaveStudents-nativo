package br.oficial.savestudents.controller

import com.airbnb.epoxy.EpoxyController
import br.oficial.savestudents.R
import br.oficial.savestudents.constants.HomeConstants
import br.oficial.savestudents.holder.homeHorizontalCardHolder
import br.oficial.savestudents.holder.responseErrorHolder
import br.oficial.savestudents.model.SubjectList
import br.oficial.savestudents.model.contract.HomeActivityContract
import br.oficial.savestudents.model.error.SubjectListErrorModel
import br.oficial.savestudents.ui_component.shimmer.shimmerHolder
import br.oficial.savestudents.ui_component.title.titleHolder

class SearchBarController(private val mContract: HomeActivityContract) : EpoxyController() {
    private var mSubjectList: MutableList<SubjectList> = mutableListOf()
    private lateinit var mResponseError: SubjectListErrorModel
    private var isResponseError: Boolean = false
    private var loading: Boolean = false

    override fun buildModels() {
        if (isResponseError) {
            handleResponseError()
        } else {
            handleSearchList()
        }
    }

    private fun handleSearchList() {
        titleHolder {
            id("title_holder")
            title("Você está buscando por")
            marginBottom(8)
            marginLeft(16)
        }

        if (loading) {
            shimmerHolder {
                id("shimmer")
                layout(R.layout.home_horizontal_card_shimmer)
                marginLeft(16)
                marginRight(16)
            }
        } else {
            mSubjectList.forEach { item ->
                homeHorizontalCardHolder {
                    id(item.id)
                    subjectId(item.id)
                    title(item.subjectName)
                    period(item.period)
                    clickHorizontalCardListener(mContract::clickHorizontalCardListener)
                    marginLeft(16)
                    marginRight(16)
                    marginBottom(8)
                }
            }
        }
    }

    private fun handleResponseError() {
        responseErrorHolder {
            id(HomeConstants.Filter.TYPE_SEARCH_ERROR)
            message(mResponseError.message)
            description(mResponseError.description)
        }
    }

    fun setSubjectList(list: List<SubjectList>) {
        mSubjectList = list.toMutableList()
        requestModelBuild()
    }

    fun setLoading(status: Boolean) {
        loading = status
        requestModelBuild()
    }

    fun isResponseError(error: Boolean) {
        isResponseError = error
    }

    fun setResponseError(responseError: SubjectListErrorModel) {
        mResponseError = responseError
        requestModelBuild()
    }
}