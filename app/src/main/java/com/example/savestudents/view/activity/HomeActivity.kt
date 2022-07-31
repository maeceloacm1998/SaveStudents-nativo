package com.example.savestudents.view.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.savestudents.R
import com.example.savestudents.controller.HeaderMainViewController
import com.example.savestudents.controller.HomeMainViewController
import com.example.savestudents.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    private val headerMainViewController = HeaderMainViewController()
    private val homeMainViewController = HomeMainViewController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controllers()
    }

    private fun controllers() {
        handleHeaderController()
        handleHomeController()
    }

    private fun setTallestHeaderController() {
        val viewGroup = window.decorView.findViewById<ViewGroup>(android.R.id.content)

    }

    private fun handleHeaderController() {
        setTallestHeaderController()
        binding.headerMainView.apply {
            setController(headerMainViewController)
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun handleHomeController() {
        binding.homeMainView.apply {
            setController(homeMainViewController)
            layoutManager = LinearLayoutManager(context)
        }
    }
}