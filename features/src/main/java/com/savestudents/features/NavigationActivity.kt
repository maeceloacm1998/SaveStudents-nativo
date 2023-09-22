package com.savestudents.features

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import com.savestudents.core.utils.InitialScreenTypes
import com.savestudents.features.databinding.ActivityNavigationBinding

class NavigationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        handleUserLogger()
        setContentView(binding.root)
    }

    private fun handleUserLogger() {
        val intent = intent.extras
        val initialScreen = intent?.getSerializable(INITIAL_SCREEN_TYPES) as InitialScreenTypes
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view_login) as NavHostFragment
        val controller = navHostFragment.navController

        when (initialScreen) {
            InitialScreenTypes.HOME -> {
                controller.navigate(R.id.addMatterFragment)
            }

            InitialScreenTypes.LOGIN -> {
                controller.navigate(R.id.loginFragment)
            }
        }
    }

    fun handleDrawerMenu() {
        visibleToolbar(true)
        val actionBarToggle = ActionBarDrawerToggle(this, binding.drawerLayout, 0, 0)
        binding.run {
            toolbar.withNotification = true
            toolbar.title = ""
            drawerLayout.addDrawerListener(actionBarToggle)
            actionBarToggle.syncState()

            toolbar.root.setNavigationOnClickListener {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }

    fun goBackPressed(action: () -> Unit) {
        binding.toolbar.root.setNavigationOnClickListener {
            action()
        }
    }

    fun handleTitleToolbar(title: String) {
        visibleToolbar(true)
        binding.toolbar.withNotification = false
        binding.toolbar.title = title
    }

    fun visibleToolbar(visible: Boolean) {
        binding.toolbar.isVisible = visible
    }

    companion object {
        private const val INITIAL_SCREEN_TYPES = "initial_screen_types"
        fun newInstance(context: Context, initialScreen: InitialScreenTypes): Intent {
            val intent = Intent(context, NavigationActivity::class.java)
            val bundle = Bundle().apply {
                this.putSerializable(INITIAL_SCREEN_TYPES, initialScreen)
            }
            intent.putExtras(bundle)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            return intent
        }
    }
}