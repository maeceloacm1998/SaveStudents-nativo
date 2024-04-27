package com.savestudents.features

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.savestudents.components.R.id
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.savestudents.components.bottomSheet.CustomBottomSheetFragment
import com.savestudents.components.snackbar.SnackBarCustomType
import com.savestudents.components.snackbar.SnackBarCustomView
import com.savestudents.core.utils.InitialScreenTypes
import com.savestudents.features.databinding.ActivityNavigationBinding

class NavigationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNavigationBinding
    private var controller: NavController? = null
    private lateinit var customBottomSheet: CustomBottomSheetFragment<ViewBinding>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navController) as NavHostFragment
        controller = navHostFragment.navController

        handleInitialScreen()
        setContentView(binding.root)
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

    fun showSnackBar(title: String, snackBarCustomType: SnackBarCustomType) {
        SnackBarCustomView.show(
            view = binding.navView,
            title = title,
            snackBarCustomType = snackBarCustomType
        )
    }

    @SuppressLint("RestrictedApi")
    fun handleDrawerMenu() {
        supportActionBar?.dispatchMenuVisibilityChanged(true)
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
            handleMenuOptions()
        }
    }

    /**
     * Essa função serve para mostrar o bottomSheet. Como parametro, envie o binding inflado, que
     * sera retornado de volta para você. Ex: parentActivity?.showBottomSheet(TesteBinding.inflate(layoutInflater))
     * @param binding
     * @return binding
     */
    fun <T : ViewBinding> showBottomSheet(binding: T): T {
        customBottomSheet = CustomBottomSheetFragment(binding)
        customBottomSheet.show(supportFragmentManager, "customBottomSheet")
        return binding
    }

    fun hideBottomSheet() {
        customBottomSheet.dismiss()
    }

    private fun handleInitialScreen() {
        val intent = intent.extras

        when (intent?.getSerializable(INITIAL_SCREEN_TYPES) as InitialScreenTypes) {
            InitialScreenTypes.HOME -> controller?.navigate(R.id.action_loginFragment_to_homeFragment)

            InitialScreenTypes.LOGIN -> controller?.navigate(R.id.loginFragment)
        }
    }

    private fun handleMenuOptions() {
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                id.nav_schedule -> {
                    onSetDrawerNavigate(R.id.curriculumFragment)
                    true
                }

                id.nav_notification -> {
                    onSetDrawerNavigate(R.id.curriculumFragment)
                    true
                }

                id.nav_config -> {
                    onSetDrawerNavigate(R.id.configFragment)
                    true
                }

                id.notification -> {
                    onSetDrawerNavigate(R.id.curriculumFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun onSetDrawerNavigate(screenId: Int) {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        controller?.navigate(screenId)
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