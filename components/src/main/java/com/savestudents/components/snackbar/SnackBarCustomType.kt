package com.savestudents.components.snackbar

import com.savestudents.components.R

enum class SnackBarCustomType(val background: Int, val textColor: Int) {
    SUCCESS(R.color.success_primary, R.color.white),
    ERROR(R.color.error_primary, R.color.white),
    NORMAL(R.color.primary, R.color.white)
}