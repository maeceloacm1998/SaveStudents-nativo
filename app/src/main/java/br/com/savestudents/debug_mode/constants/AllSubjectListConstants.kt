package br.com.savestudents.debug_mode.constants

class AllSubjectListConstants {
    object Filter {
        const val SHIFT_FIELD = "shift"
        const val PERIOD_FIELD = "period"
        const val MESSAGE_ERROR = "Ohhh não...no momento está vazio."
        const val DESCRIPTION_ERROR = "Comece cadastrando uma ou mais matérias"
        const val DESCRIPTION_SEARCH_ERROR = "Tente buscar por outra palavra"
        const val TYPE_LIST_ERROR = "list"
        const val TYPE_FILTER_ERROR = "filter"
        const val TYPE_SEARCH_ERROR = "Search"
    }

    object Dialog {
        const val DELETE_SUBJECT_TITLE = "Excluir"
        const val DELETE_SUBJECT_DESCRIPTION =
            "Caso confirme, essa matérias será deletada permanentemente, assim perdendo todos os dados. Deseja excluir?"
    }
}