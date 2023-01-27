package br.oficial.savestudents.debug_mode.repository

import com.br.core.constants.FirestoreDbConstants
import br.oficial.savestudents.debug_mode.model.repository.IAllSubjectList
import com.br.core.service.external.FirebaseClient

class AllSubjectsListRepository: IAllSubjectList {
    private val client = FirebaseClient()

    override fun deleteSubjectItem(id: String) {
        client.deleteDocument(FirestoreDbConstants.Collections.SUBJECTS_LIST, id)
        client.deleteDocument(FirestoreDbConstants.Collections.TIMELINE_LIST, id)
    }
}