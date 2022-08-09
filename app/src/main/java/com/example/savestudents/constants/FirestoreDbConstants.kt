package com.example.savestudents.constants

class FirestoreDbConstants {
    object StatusCode {
        const val SUCCESS = 200
        const val NOT_FOUND = 404
    }

    object MethodsFirebaseClient {
        const val GET_DOCUMENT_VALUE = "GET_DOCUMENT_VALUE"
        const val GET_SPECIFIC_DOCUMENT = "GET_SPECIFIC_DOCUMENT"
        const val SET_DOCUMENT = "SET_DOCUMENT"
        const val PUT_DOCUMENT = "PUT_DOCUMENT"
        const val DELETE_DOCUMENT = "DELETE_DOCUMENT"
    }

    object MessageError {
        const val EMPTY_RESULT = "Lista vazia ou aparelho celular sem conexao."
    }

    object Collections {
        const val SUBJECTS_LIST = "subjects_list"
        const val FILTER_OPTIONS_SHIFT = "filter_options_shift"
        const val FILTER_OPTIONS_PERIOD = "filter_options_period"
    }
}