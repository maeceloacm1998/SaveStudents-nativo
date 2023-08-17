package com.savestudents.features.login.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.savestudents.features.databinding.ActivityLoginV2Binding
import com.savestudents.features.login.di.LoginDependencyInjection
import com.savestudents.features.shared.utils.KoinUtils
import com.savestudents.features.shared.utils.KoinUtils.addModules

class LoginV2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginV2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginV2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        KoinUtils.createInstance(applicationContext)
        addModules(*LoginDependencyInjection.modules)
    }
}