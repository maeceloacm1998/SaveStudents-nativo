package br.oficial.savestudents.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.oficial.savestudents.R
import br.oficial.savestudents.view.activity.OnboardActivity
import com.br.core.notifications.NotificationsManager
import com.br.core.service.internal.database.AdminCheckDB
import com.br.core.service.sharedPreferences.SharedPreferencesBuilderR1
import com.savestudents.core.accountManager.AccountManagerDependencyInjection
import com.savestudents.core.firebase.FirebaseDependencyInjection
import com.savestudents.core.sharedPreferences.SharedPreferencesDependencyInjection
import com.savestudents.features.accountRegister.di.AccountRegisterDependencyInjection
import com.savestudents.features.login.di.LoginDependencyInjection
import com.savestudents.features.NavigationActivity
import com.savestudents.features.shared.utils.KoinUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getPushToken()
        koinInjection()
        initAdminCheckDb()
        handleInitialActivity()
    }

    private fun koinInjection() {
        KoinUtils.createInstance(applicationContext)
        KoinUtils.addModules(*FirebaseDependencyInjection.modules)
        KoinUtils.addModules(*AccountManagerDependencyInjection.modules)
        KoinUtils.addModules(*SharedPreferencesDependencyInjection.modules)
        KoinUtils.addModules(*LoginDependencyInjection.modules)
        KoinUtils.addModules(*AccountRegisterDependencyInjection.modules)
    }

    private fun initAdminCheckDb() {
        AdminCheckDB.getDataBase(applicationContext).adminCheckDAO()
    }

    private fun getPushToken() {
        val pushToken = SharedPreferencesBuilderR1.GetInstance(applicationContext)
            .getString(NotificationsManager.PUSH_TOKEN_KEY)

        if (pushToken.isNullOrBlank()) {
            NotificationsManager(applicationContext).getPushToken()
        }
    }

    private fun handleInitialActivity() {
        val shouldShowOnboard = SharedPreferencesBuilderR1.GetInstance(applicationContext)
            .getBoolean(OnboardActivity.ONBOARD_KEY, true)

        if (shouldShowOnboard) {
            renderOnboard()
        } else {
            renderHome()
        }
    }

    private fun renderHome() {
        val intent = Intent(applicationContext, NavigationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)
    }

    private fun renderOnboard() {
        val intent = OnboardActivity.newInstance(applicationContext)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)
    }
}