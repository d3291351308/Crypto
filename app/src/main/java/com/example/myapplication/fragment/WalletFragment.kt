package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.WalletAdapter
import com.example.myapplication.databinding.WalletFragmentBinding
import com.example.myapplication.model.WalletRepository
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.vm.WalletViewModel
import com.example.myapplication.vm.WalletViewModelFactory

class WalletFragment : BaseFragment<WalletFragmentBinding>() {
    private lateinit var viewModel: WalletViewModel
    private val adapter = WalletAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // 使用工厂创建 ViewModel 实例
        viewModel = ViewModelProvider(
            requireActivity(), WalletViewModelFactory(
                WalletRepository(
                    RetrofitClient.getInstance(requireActivity()).currencyService,
                    RetrofitClient.getInstance(requireActivity()).walletService,
                    RetrofitClient.getInstance(requireActivity()).rateService
                )
            )
        )[WalletViewModel::class.java]

        if (savedInstanceState == null) {
            viewModel.loadData()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun initView() {
        binding.rvCryptoList.apply {
            adapter = this@WalletFragment.adapter
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), DividerItemDecoration.VERTICAL
                )
            )
            setHasFixedSize(true)
        }

        binding.tvEmptyView.setOnClickListener {
            viewModel.loadData()
        }
    }

    override fun setupObservers() {
        viewModel.currencyItems.observe(viewLifecycleOwner) { items ->
            adapter.updateItems(items)
            binding.tvEmptyView.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.totalValue.observe(viewLifecycleOwner) { total ->
            binding.tvTotalBalance.text = "$${"%.4f".format(total)}"
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): WalletFragmentBinding {
        return WalletFragmentBinding.inflate(inflater, container, false)
    }
}