package com.example.myapplication.vm

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.WalletItem
import com.example.myapplication.model.WalletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WalletViewModel(
    private val repository: WalletRepository
) : ViewModel() {

    private val _currencyItems = MutableLiveData<List<WalletItem>>()
    val currencyItems: LiveData<List<WalletItem>> = _currencyItems

    private val _totalValue = MutableLiveData<Double>().apply { value = 0.0 }
    val totalValue: LiveData<Double> = _totalValue

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val refreshRunnable = object : Runnable {
        override fun run() {
            loadData()
            handler.postDelayed(this, 1000) // 刷新
        }
    }

    private val handler = Handler(Looper.getMainLooper())

    init {
        handler.post(refreshRunnable)
    }

    fun loadData() {
        _error.value = null

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val items = repository.getCurrencyItems()
                withContext(Dispatchers.Main) {
                    _currencyItems.value = items

                    // 计算总价值
                    val total = items.sumOf { it.usdValue }
                    _totalValue.value = total
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.value = "Failed to load data: ${e.message}"
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacks(refreshRunnable)
    }
}