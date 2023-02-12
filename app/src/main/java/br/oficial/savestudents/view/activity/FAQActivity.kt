package br.oficial.savestudents.view.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.oficial.savestudents.R
import br.oficial.savestudents.databinding.ActivityFaqactivityBinding
import com.br.core.share.ShareManager

class FAQActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFaqactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleBackButton()
        handleGmailButtonListener()
        handleWppButtonListener()
        handleLinkedinButtonListener()
        handleClickProfileListener()
    }

    private fun handleBackButton() {
        binding.backContainer.setOnClickListener {
            finish()
        }
    }

    private fun handleGmailButtonListener() {
        binding.email.setOnClickListener {
            startActivity(
                ShareManager().gmailShare(
                    application.getString(R.string.default_title_email),
                    application.getString(R.string.default_text_email)
                )
            )
        }
    }

    private fun handleWppButtonListener() {
        binding.wpp.setOnClickListener {
            startActivity(
                ShareManager().wppShare(application.getString(R.string.default_text_email))
            )
        }
    }

    private fun handleLinkedinButtonListener() {
        binding.linkedin.setOnClickListener {
            startActivity(ShareManager().linkedinShare())
        }
    }

    private fun handleClickProfileListener() {
        binding.profile.profileContainer.setOnClickListener {
            startActivity(ShareManager().linkedinShare())
        }
    }

    companion object {
        fun newInstance(context: Context): Intent {
            return Intent(context, FAQActivity::class.java)
        }
    }
}