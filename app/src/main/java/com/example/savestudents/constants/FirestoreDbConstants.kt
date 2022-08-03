package com.example.savestudents.constants

class FirestoreDbConstants {
    object StatusCode {
        const val NOT_FOUND = 404
        const val BAD_REQUEST = 400
    }

    object MessageError {
        const val EMPTY_RESULT = "Lista vazia"
    }

    object Subject {
        const val SUBJECTS_LIST = "subjects_list"
    }
}