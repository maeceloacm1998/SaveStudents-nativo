package br.oficial.savestudents.debug_mode.repository

import br.oficial.savestudents.constants.FirestoreDbConstants
import br.oficial.savestudents.debug_mode.model.repository.IAllSubjectList
import br.oficial.savestudents.service.external.FirebaseClient

class AllSubjectsListRepository: IAllSubjectList {
    private val client = FirebaseClient()

    override fun deleteSubjectItem(id: String) {
        client.deleteDocument(FirestoreDbConstants.Collections.SUBJECTS_LIST, id)
        client.deleteDocument(FirestoreDbConstants.Collections.TIMELINE_LIST, id)
    }
}