package br.oficial.savestudents.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.oficial.savestudents.R
import com.br.core.notifications.NotificationsManager
import com.br.core.service.internal.database.AdminCheckDB
import com.br.core.service.sharedPreferences.SharedPreferencesBuilderR1
import com.savestudents.core.accountManager.AccountManager
import com.savestudents.core.accountManager.AccountManagerDependencyInjection
import com.savestudents.core.firebase.FirebaseDependencyInjection
import com.savestudents.core.notification.NotificationManagerDependencyInjection
import com.savestudents.core.sharedPreferences.SharedPreferencesDependencyInjection
import com.savestudents.core.utils.InitialScreenTypes
import com.savestudents.features.accountRegister.di.AccountRegisterDependencyInjection
import com.savestudents.features.login.di.LoginDependencyInjection
import com.savestudents.features.NavigationActivity
import com.savestudents.features.addMatter.di.AddMatterDependencyInjection
import com.savestudents.features.config.di.ConfigDependencyInjection
import com.savestudents.features.curriculum.di.CurriculumDependencyInjection
import com.savestudents.features.event.di.AddEventDependencyInjection
import com.savestudents.features.home.di.HomeDependencyInjection
import com.savestudents.features.notificationconfig.di.NotificationConfigDependencyInjection
import com.savestudents.features.shared.utils.KoinUtils
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val accountRegister: AccountManager by inject()

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
        KoinUtils.addModules(*NotificationManagerDependencyInjection.modules)
        KoinUtils.addModules(*LoginDependencyInjection.modules)
        KoinUtils.addModules(*AccountRegisterDependencyInjection.modules)
        KoinUtils.addModules(*HomeDependencyInjection.modules)
        KoinUtils.addModules(*AddMatterDependencyInjection.modules)
        KoinUtils.addModules(*CurriculumDependencyInjection.modules)
        KoinUtils.addModules(*AddEventDependencyInjection.modules)
        KoinUtils.addModules(*ConfigDependencyInjection.modules)
        KoinUtils.addModules(*NotificationConfigDependencyInjection.modules)
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
        if (accountRegister.isLoggedUser()) {
            lifecycleScope.launch {
                val user = accountRegister.getUserAccount()

                user?.let {
                    val login = accountRegister.login(it.email, it.password)

                    login.onSuccess {
                        startActivity(
                            NavigationActivity.newInstance(
                                applicationContext,
                                InitialScreenTypes.HOME
                            )
                        )
                    }
                }
            }
        } else {
            startActivity(
                NavigationActivity.newInstance(
                    applicationContext,
                    InitialScreenTypes.LOGIN
                )
            )
        }
    }
}