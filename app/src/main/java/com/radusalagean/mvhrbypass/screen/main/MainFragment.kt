package com.radusalagean.mvhrbypass.screen.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.radusalagean.mvhrbypass.R
import com.radusalagean.mvhrbypass.databinding.FragmentMainBinding
import com.radusalagean.mvhrbypass.generic.fragment.BaseFragment
import com.radusalagean.mvhrbypass.network.SocketManager
import com.radusalagean.mvhrbypass.network.SocketSubscriber
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.layout_table.*

class MainFragment : BaseFragment(), SocketSubscriber {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val socketManager: SocketManager by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.activityContract = getActivityContract()
        binding.lifecycleOwner = this
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
        socketManager.subscribe(this)
    }

    override fun unregisterListeners() {
        table_cell_current_mode.setOnLongClickListener(null)
        socketManager.unsubscribe(this)
    }

    override fun loadData() {
        viewModel.connect()
    }

    override fun onFailure() {
        getActivityContract().run {
            showErrorMessage(R.string.message_connection_failed)
            popAllFragments()
        }
    }

    override fun getInfoBarContainer(): ViewGroup = fragment_main_root_view

    companion object {

        const val ARG_SOCKET_ADDRESS = "socket_address"

        fun newInstance(socketAddress: String): MainFragment {
            val fragment = MainFragment()
            val args = bundleOf(ARG_SOCKET_ADDRESS to socketAddress)
            fragment.arguments = args
            return fragment
        }
    }
}