package com.savestudents.components.snackbar

import android.view.View
import com.google.android.material.snackbar.Snackbar

class SnackBarCustomView {
    companion object {
        fun show(
            view: View,
            title: String, snackBarCustomType: SnackBarCustomType
        ) {
            Snackbar.make(view, title, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(view.context.getColor(snackBarCustomType.background))
                .setTextColor(view.context.getColor(snackBarCustomType.textColor))
                .show()
        }
    }
}
