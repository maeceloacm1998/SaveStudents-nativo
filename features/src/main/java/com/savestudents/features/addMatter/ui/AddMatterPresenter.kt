package com.savestudents.features.addMatter.ui

import com.savestudents.core.firebase.FirebaseClient
import com.savestudents.features.addMatter.models.Matter

class AddMatterPresenter(
    val view: AddMatterContract.View,
    private val client: FirebaseClient
) : AddMatterContract.Presenter {
    private var matterList: List<Matter> = mutableListOf()
    private var matterSelected: Matter? = null
    private var initialTime: String? = null
    private var finalTime: String? = null

    override fun start() {
        view.loading(true)
    }

    override suspend fun fetchMatters() {
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

    override fun saveInitialHourSelected(time: String) {
        initialTime = time
    }

    override fun saveFinalHourSelected(time: String) {
        finalTime = time
    }

    override suspend fun registerMatter(daysSelected: List<String>) {
        when {
            // TODO implemenmtar cada validacao na camada de view
            matterSelected == null -> {}
            initialTime == null -> {}
            finalTime == null -> {}
            daysSelected.isEmpty() -> {}
            else -> {}
        }
    }

    suspend fun register() {
        // TODO fazer request e salvar isso.
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
