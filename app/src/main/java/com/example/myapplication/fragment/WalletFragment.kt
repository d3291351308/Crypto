package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.WalletAdapter
import com.example.myapplication.databinding.WalletFragmentBinding
import com.example.myapplication.model.WalletRepository
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.vm.WalletViewModel
import com.example.myapplication.vm.WalletViewModelFactory

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
                    RetrofitClient.getInstance(requireContext()).currencyService,
                    RetrofitClient.getInstance(requireContext()).walletService,
                    RetrofitClient.getInstance(requireContext()).rateService)
            )
        )[WalletViewModel::class.java]

        setupRecyclerView()
        setupObservers()
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
        viewModel.currencyItems.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
            binding.tvEmptyView.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.totalValue.observe(viewLifecycleOwner) { total ->
            binding.tvTotalBalance.text = "$${"%.4f".format(total)}"
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.clearError() // 清除错误状态
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}