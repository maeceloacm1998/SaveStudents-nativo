package com.savestudents.components.appbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.appbar.MaterialToolbar
import com.savestudents.components.R
import com.savestudents.components.databinding.AppBarCustomBinding

class AppBarCustomView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    private val binding: AppBarCustomBinding = AppBarCustomBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    var title: String = ""
        set(value) {
            field = value
            binding.topAppBar.title = value
        }

    var root: MaterialToolbar = binding.topAppBar

    private lateinit var clickNotificationListener: () -> Unit?

    init {
        setLayout(attrs)
    }

    private fun setLayout(attr: AttributeSet?) {
        attr?.let { attributeSet: AttributeSet ->
            val attributes =
                context.obtainStyledAttributes(attributeSet, R.styleable.AppBarCustomView)
            val isToolbarWithNotification =
                attributes.getBoolean(R.styleable.AppBarCustomView_withNotification, false)

            if (isToolbarWithNotification) {
                setToolbarWithNotification()
            } else {
                binding.topAppBar.title =
                    attributes.getString(R.styleable.AppBarCustomView_android_title)
            }

            attributes.recycle()
        }
    }

    private fun setToolbarWithNotification() {
        binding.topAppBar.inflateMenu(R.menu.menu_app_bar_custom_view)
        binding.topAppBar.navigationIcon = getDrawable(context, R.drawable.ic_menu_24)
        handleNotificationListener()
    }

    private fun handleNotificationListener() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.notification -> {
                    if (::clickNotificationListener.isInitialized) {
                        clickNotificationListener()
                    }
                    true
                }

                else -> false
            }
        }
    }

    fun onNotificationListener(listener: () -> Unit) {
        clickNotificationListener = listener
    }
}