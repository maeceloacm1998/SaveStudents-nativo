package com.br.core.contract

import android.content.Intent

interface ShareManagerContract {
    fun androidShareSheet(text: String): Intent
}