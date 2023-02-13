package com.br.core.contract

import android.content.Intent

interface ShareManagerContract {
    fun androidShareSheet(text: String): Intent
    fun gmailShare(title: String, description: String): Intent
    fun wppShare(description: String): Intent
    fun linkedinShare(): Intent
}