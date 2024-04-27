package com.savestudents.core.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class InitialScreenTypes: Parcelable {
    HOME,
    LOGIN
}