package com.radusalagean.mvhrbypass.screen.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import com.radusalagean.mvhrbypass.R
import com.radusalagean.mvhrbypass.databinding.FragmentMainBinding
import com.radusalagean.mvhrbypass.generic.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.layout_table.*

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
        table_cell_current_mode.setOnLongClickListener {
            viewModel.onModeLongClick(getActivityContract())
            true
        }
    }

    override fun unregisterListeners() {
        table_cell_current_mode.setOnLongClickListener(null)
    }

    override fun loadData() {
        viewModel.hrModeText.value = getString(R.string.mode_auto)
        viewModel.hrModeBackground.value = ResourcesCompat.getDrawable(
            resources,
            R.drawable.table_cell_hr_enabled_background,
            requireContext().theme
        )
    }

    override fun getInfoBarContainer(): ViewGroup = fragment_main_root_view

    companion object {
        fun newInstance() = MainFragment()
    }
}