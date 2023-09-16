package com.savestudents.core.accountManager.model

data class UserAccount(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val institution: String = "",
    val birthDate: String = "",
    val password: String = ""
)