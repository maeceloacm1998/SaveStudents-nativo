package com.savestudents.features.addMatter.ui

import com.savestudents.core.firebase.FirebaseClient
import com.savestudents.features.addMatter.models.Matter

class AddMatterPresenter(
    val view: AddMatterContract.View,
    private val client: FirebaseClient
) : AddMatterContract.Presenter {
    private var matterList: List<Matter> = mutableListOf()
    private var matterSelected: Matter? = null

    override fun start() {}

    override suspend fun fetchMatters() {
        view.loading(true)

        client.getDocumentValue("matterList").onSuccess {
            val matterListItems = it.map { item -> checkNotNull(item.toObject(Matter::class.java)?.matterName) }
            matterList = it.map { item -> checkNotNull(item.toObject(Matter::class.java)) }
            view.setMatterOptions(matterListItems)
            view.loading(false)
        }.onFailure {
            view.showError()
        }
    }

    override fun matterSelect(option: String) {
        val matter = checkNotNull(matterList.find { matter -> matter.matterName == option })
        view.handleMatterSelect(matter)
        matterSelected = matter
    }
}

data class Exemple(
    val subjectsInformation: Subject? = null,
    val timelineList: List<Matter.Timeline> = mutableListOf()
) {
    data class Subject(
        val id: String = "",
        val period: String = "",
        val teacherName: String = "",
        val subjectName: String = ""
    )
}
