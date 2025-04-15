package com.example.myapplication.model

data class WalletCurrency(
    val id: String,          // 币种ID (如 "bitcoin")
    val name: String,        // 币种名称 (如 "Bitcoin")
    val symbol: String,      // 币种符号 (如 "BTC")
    val imageUrl: String     // 图标URL
)

data class WalletBalance(
    val currencyId: String,  // 匹配CryptoCurrency的id
    val amount: Double       // 钱包余额
)

data class LiveRate(
    val currencyId: String,  // 匹配CryptoCurrency的id
    val usdRate: Double,     // 对美元汇率
)

data class WalletItem(
    val currency: WalletCurrency,
    val balance: WalletBalance,
    val rate: LiveRate,
    val usdValue: Double = balance.amount * rate.usdRate // 计算出的美元价值
)