package com.radusalagean.mvhrbypass.screen.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.radusalagean.mvhrbypass.databinding.FragmentMainBinding
import com.radusalagean.mvhrbypass.generic.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.activityContract = getActivityContract()
        return binding.root
    }

    override fun initViews() {
        
    }

    override fun disposeViews() {
        
    }

    override fun registerListeners() {
        
    }

    override fun unregisterListeners() {
        
    }

    override fun loadData() {
        
    }

    override fun getInfoBarContainer(): ViewGroup = fragment_main_root_view

    companion object {
        fun newInstance() = MainFragment()
    }
}