package com.savestudents.features.home.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.savestudents.features.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val actionBarToggle = ActionBarDrawerToggle(this.activity, binding.drawerLayout, 0, 0)
        binding.drawerLayout.addDrawerListener(actionBarToggle)
        actionBarToggle.syncState()

        binding.toolbar.root.setNavigationOnClickListener {
            it.setOnClickListener {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        return binding.root
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {

            }
    }
}