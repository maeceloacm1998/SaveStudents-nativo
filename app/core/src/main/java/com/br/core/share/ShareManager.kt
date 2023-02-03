package com.br.core.share

import android.content.Intent
import com.br.core.contract.ShareManagerContract

class ShareManager : ShareManagerContract {
    override fun androidShareSheet(text: String): Intent {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }

        return Intent.createChooser(sendIntent, null)
    }
}