package com.savestudents.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import com.savestudents.features.databinding.ActivityNavigationBinding

class NavigationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
}