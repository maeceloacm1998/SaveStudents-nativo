package com.br.core.constants

class FirestoreDbConstants {
    object StatusCode {
        const val SUCCESS = 200
        const val NOT_FOUND = 404
    }

    object MethodsFirebaseClient {
        const val GET_DOCUMENT_VALUE = "GET_DOCUMENT_VALUE"
        const val GET_SPECIFIC_DOCUMENT = "GET_SPECIFIC_DOCUMENT"
        const val GET_FILTER_DOCUMENT = "GET_FILTER_DOCUMENT"
        const val SET_SPECIFIC_DOCUMENT = "SET_SPECIFIC_DOCUMENT"
        const val DELETE_DOCUMENT = "DELETE_DOCUMENT"
    }

    object MessageError {
        const val EMPTY_RESULT = "Lista vazia ou aparelho celular sem conexão."
        const val UPDATE_ERROR = "Erro ao atualizar o item específico"
    }

    object Collections {
        const val SUBJECTS_LIST = "subjects_list"
        const val TIMELINE_LIST = "timeline_list"
        const val TIMELINE_TYPES = "timeline_item_types"
        const val FILTER_OPTIONS_SHIFT = "filter_options_shift"
        const val FILTER_OPTIONS_PERIOD = "filter_options_period"
        const val NOTIFICATION_TIMELINE_LIST = "notification_timeline_list"
    }
}