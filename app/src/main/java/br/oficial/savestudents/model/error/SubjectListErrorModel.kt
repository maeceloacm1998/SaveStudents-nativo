package br.oficial.savestudents.model.error

data class SubjectListErrorModel(
    val type: String,
    val message: String,
    val description: String
)