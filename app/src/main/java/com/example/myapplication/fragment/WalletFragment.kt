package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.WalletAdapter
import com.example.myapplication.databinding.WalletFragmentBinding
import com.example.myapplication.model.WalletRepository
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.vm.WalletViewModel
import com.example.myapplication.vm.WalletViewModelFactory
import kotlinx.coroutines.launch

class WalletFragment : Fragment() {
    private var _binding: WalletFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: WalletViewModel

    private val adapter = WalletAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WalletFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 使用工厂创建 ViewModel 实例
        viewModel = ViewModelProvider(
            this, WalletViewModelFactory(
                WalletRepository(
                )
            )
        )[WalletViewModel::class.java]

        setupRecyclerView()
        setupObservers()
        setupRefresh()
    }

    private fun setupRecyclerView() {
        binding.rvCryptoList.apply {
            adapter = this@WalletFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            setHasFixedSize(true)
        }
    }

    private fun setupObservers() {
    }

    private fun setupRefresh() {
//        binding.swipeRefresh.setOnRefreshListener {
//            viewModel.refresh()
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}