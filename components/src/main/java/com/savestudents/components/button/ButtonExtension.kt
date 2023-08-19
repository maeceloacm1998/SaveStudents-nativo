package com.savestudents.components.button

import com.google.android.material.button.MaterialButton
import com.savestudents.components.R


fun MaterialButton.disabled(disabled: Boolean) {
    this.backgroundTintList = if(disabled) {
        this.isEnabled = false
        context.getColorStateList(R.color.primary_disabled)
    } else {
        this.isEnabled = true
        context.getColorStateList(R.color.primary)
    }
}