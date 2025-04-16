package com.example.myapplication.model

import com.example.myapplication.service.CurrencyApiService
import com.example.myapplication.service.ExchangeRateApiService
import com.example.myapplication.service.ExchangeRateApiService.Rate
import com.example.myapplication.service.WalletApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WalletRepository(
    private val currencyService: CurrencyApiService, private val walletService: WalletApiService, private val rateService: ExchangeRateApiService
) {
    private val cachedItems = mutableMapOf<String, WalletItem>()

    suspend fun getCurrencyItems(): List<WalletItem> = withContext(Dispatchers.IO) {
        try {
            val currencyRep = currencyService.getWalletCurrencies()
            val balanceRep = walletService.getWalletBalances()
            val rateRep = rateService.getLiveRates()

            // 合并数据
            val items = mutableListOf<WalletItem>()

            for (currency in currencyRep.currencies) {
                val balance = balanceRep.wallet.find { it.currencyId == currency.coin_id }
                val rate = rateRep.tiers.find { it.currencyId == currency.coin_id }

                if (balance != null && rate != null) {
                    val item = WalletItem(
                        currency = WalletCurrency(
                            id = currency.coin_id, name = currency.name, symbol = currency.symbol, imageUrl = currency.imageUrl
                        ), balance = WalletBalance(
                            currencyId = balance.currencyId, amount = balance.amount
                        ), rate = LiveRate(
                            currencyId = rate.currencyId, usdRate = rate.rates
                        )
                    )
                    items.add(item)
                    cachedItems[balance.currencyId] = item
                }
            }
            items.sortedByDescending { it.usdValue }
//            for (balance in balanceRep.wallet) {
//                val currency = currencyRep.currencies.find { it.coin_id == balance.currencyId }
//                val rate = rateRep.tiers.find { it.currencyId == balance.currencyId }
//
//                if (currency != null && rate != null) {
//                    val item = WalletItem(
//                            currency = WalletCurrency(
//                                    id = currency.coin_id,
//                                    name = currency.name,
//                                    symbol = currency.symbol,
//                                    imageUrl = currency.imageUrl
//                            ),
//                            balance = WalletBalance(
//                                    currencyId = balance.currencyId,
//                                    amount = balance.amount
//                            ),
//                            rate = LiveRate(
//                                    currencyId = rate.currencyId,
//                                    usdRate = rate.usdRate
//                            )
//                    )
//                    items.add(item)
//                    cachedItems[balance.currencyId] = item
//                }
//            }

        } catch (e: Exception) {
            // 出错时返回缓存数据
            cachedItems.values.toList().sortedByDescending { it.usdValue }
        }
    }
}