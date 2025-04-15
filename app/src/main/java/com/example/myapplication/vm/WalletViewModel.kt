package com.example.myapplication.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.WalletItem
import com.example.myapplication.model.WalletRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WalletViewModel(
    private val repository: WalletRepository
) : ViewModel() {

}