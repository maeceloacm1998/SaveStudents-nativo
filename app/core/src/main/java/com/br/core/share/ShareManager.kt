package com.br.core.share

import android.content.Intent
import android.net.Uri
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

    override fun gmailShare(title: String, description: String): Intent {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("marcelochmendes@gmail.com"))
        intent.putExtra(Intent.EXTRA_SUBJECT, title)
        intent.putExtra(Intent.EXTRA_TEXT, description)
        return intent
    }

    override fun wppShare(description: String): Intent {
        val urlString = Uri.parse("https://api.whatsapp.com/send?phone=31992521566&text=${description}")
        val intent = Intent(Intent.ACTION_VIEW, urlString)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        return intent
    }

    override fun linkedinShare(): Intent {
        val urlString = Uri.parse("https://www.linkedin.com/in/marcelochmendes/")
        val intent = Intent(Intent.ACTION_VIEW, urlString)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.linkedin.android");
        return intent
    }
}