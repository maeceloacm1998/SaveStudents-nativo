package com.example.savestudents.view.activity

import android.R
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.savestudents.controller.HeaderMainViewController
import com.example.savestudents.controller.HomeMainViewController
import com.example.savestudents.databinding.ActivityHomeBinding
import com.example.savestudents.viewModel.HomeViewModel

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    private val headerMainViewController = HeaderMainViewController()
    private val homeMainViewController = HomeMainViewController()
    private lateinit var mViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(
            application
        )).get(HomeViewModel()::class.java)

        fetchSubjectList()
        controllers()
        observers()
    }

    private fun controllers() {
        handleHeaderController()
        handleHomeController()
    }

    private fun observers() {
        mViewModel.subjectList.observe(this) { observe ->
            homeMainViewController.setSubjectList(observe)
        }
    }

    private fun fetchSubjectList() {
        mViewModel.getSubjectList()
    }

    private fun setTallestHeaderController() {
        val viewGroup = window.decorView.findViewById<ViewGroup>(R.id.content)
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

