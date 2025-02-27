package com.radusalagean.mvhrbypass.screen.connect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.radusalagean.mvhrbypass.databinding.FragmentConnectBinding
import com.radusalagean.mvhrbypass.generic.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_connect.*

class ConnectFragment : BaseFragment() {

    private lateinit var binding: FragmentConnectBinding
    private val viewModel: ConnectViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConnectBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.activityContract = getActivityContract()
        binding.lifecycleOwner = this
        return binding.root
    }

    // BaseFragment implementation

    override fun initViews() {
    }

    override fun disposeViews() {
    }

    override fun registerListeners() {

    }

    override fun unregisterListeners() {

    }

    override fun loadData() {
        viewModel.loadData()
    }

    override fun getInfoBarContainer(): ViewGroup = fragment_connect_root_view

    companion object {
        fun newInstance() = ConnectFragment()
    }
}