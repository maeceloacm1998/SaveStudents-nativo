package br.oficial.savestudents.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.oficial.savestudents.R
import br.oficial.savestudents.view.activity.HomeActivity
import br.oficial.savestudents.view.activity.OnboardActivity
import com.br.core.notifications.NotificationsManager
import com.br.core.service.internal.database.AdminCheckDB
import com.br.core.service.sharedPreferences.SharedPreferencesBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getPushToken()
        initAdminCheckDb()
        renderHomeActivity()
    }

    private fun initAdminCheckDb() {
        AdminCheckDB.getDataBase(applicationContext).adminCheckDAO()
    }

    private fun getPushToken() {
        val pushToken = SharedPreferencesBuilder.GetInstance(applicationContext)
            .getString(NotificationsManager.PUSH_TOKEN_KEY)

        if (pushToken.isNullOrBlank()) {
            NotificationsManager(applicationContext).getPushToken()
        }
    }

    private fun renderHomeActivity() {
        val intent = HomeActivity.newInstance(applicationContext)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val intentTest = Intent(applicationContext, OnboardActivity::class.java)
        startActivity(intentTest)
    }
}