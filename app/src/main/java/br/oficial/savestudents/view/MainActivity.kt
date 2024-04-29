package br.oficial.savestudents.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.oficial.savestudents.R
import com.savestudents.core.accountManager.AccountManager
import com.savestudents.core.accountManager.AccountManagerDependencyInjection
import com.savestudents.core.firebase.FirebaseDependencyInjection
import com.savestudents.core.notification.NotificationManagerDependencyInjection
import com.savestudents.core.notificationservice.NotificationService
import com.savestudents.core.sharedPreferences.SharedPreferencesDependencyInjection
import com.savestudents.core.utils.InitialScreenTypes
import com.savestudents.features.accountRegister.di.AccountRegisterDependencyInjection
import com.savestudents.features.login.di.LoginDependencyInjection
import com.savestudents.features.NavigationActivity
import com.savestudents.features.addMatter.data.di.AddMatterDependencyInjection
import com.savestudents.features.config.di.ConfigDependencyInjection
import com.savestudents.features.curriculum.data.di.CurriculumDependencyInjection
import com.savestudents.features.addEvent.data.di.AddEventDependencyInjection
import com.savestudents.features.home.di.HomeDependencyInjection
import com.savestudents.features.notificationconfig.di.NotificationConfigDependencyInjection
import com.savestudents.features.securityconfig.di.SecurityConfigDependencyInjection
import com.savestudents.features.shared.utils.KoinUtils.addModules
import com.savestudents.features.shared.utils.KoinUtils.createInstance
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val accountRegister: AccountManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        koinInjection()
        handleInitialActivity()
        initService()
    }

    private fun koinInjection() {
        createInstance(applicationContext)
        addModules(*NotificationManagerDependencyInjection.modules)
        addModules(*FirebaseDependencyInjection.modules)
        addModules(*AccountManagerDependencyInjection.modules)
        addModules(*SharedPreferencesDependencyInjection.modules)
        addModules(*LoginDependencyInjection.modules)
        addModules(*AccountRegisterDependencyInjection.modules)
        addModules(*HomeDependencyInjection.modules)
        addModules(*AddMatterDependencyInjection.modules)
        addModules(*CurriculumDependencyInjection.modules)
        addModules(*AddEventDependencyInjection.modules)
        addModules(*ConfigDependencyInjection.modules)
        addModules(*NotificationConfigDependencyInjection.modules)
        addModules(*SecurityConfigDependencyInjection.modules)
    }

    private fun initService() {
        val serviceIntent = Intent(applicationContext, NotificationService::class.java)
        startService(serviceIntent)
    }

    private fun handleInitialActivity() {
        if (accountRegister.isLoggedUser()) {
            lifecycleScope.launch {
                val user = checkNotNull(accountRegister.getUserAccount())

                goToHome(
                    email = user.email,
                    password = user.password
                )
            }
        } else {
            goToLogin()
        }
    }

    private suspend fun goToHome(email: String, password: String) {
        accountRegister.login(
            email = email,
            password = password
        ).onSuccess {
            startActivity(
                NavigationActivity.newInstance(
                    applicationContext,
                    InitialScreenTypes.HOME
                )
            )
        }.onFailure {
            goToLogin()
        }
    }

    private fun goToLogin() {
        startActivity(
            NavigationActivity.newInstance(
                applicationContext,
                InitialScreenTypes.LOGIN
            )
        )
    }
}