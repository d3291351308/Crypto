package com.example.myapplication.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.model.WalletRepository

// ViewModel Factory
class WalletViewModelFactory(
    private val repository: WalletRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WalletViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WalletViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}