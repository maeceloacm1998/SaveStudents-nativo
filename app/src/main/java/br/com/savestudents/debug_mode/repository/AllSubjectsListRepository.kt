package br.com.savestudents.debug_mode.repository

import br.com.savestudents.constants.FirestoreDbConstants
import br.com.savestudents.debug_mode.model.IAllSubjectList
import br.com.savestudents.service.external.FirebaseClient

class AllSubjectsListRepository: IAllSubjectList {
    private val client = FirebaseClient()

    override fun deleteSubjectItem(id: String) {
        client.deleteDocument(FirestoreDbConstants.Collections.SUBJECTS_LIST, id)
        client.deleteDocument(FirestoreDbConstants.Collections.TIMELINE_LIST, id)
    }
}