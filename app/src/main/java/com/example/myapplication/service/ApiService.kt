package com.example.myapplication.service

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {
    @GET("coins/markets")
    suspend fun getWalletCurrencies(
        @Query("vs_currency") vsCurrency: String = "usd",
        @Query("ids") ids: String? = null
    ): List<WalletCurrencyResponse>

    data class WalletCurrencyResponse(
        val id: String,
        val name: String,
        val symbol: String,
        @SerializedName("image") val imageUrl: String
    )
}

interface WalletApiService {
    @GET("wallet/balances")
    suspend fun getWalletBalances(): List<WalletBalanceResponse>

    data class WalletBalanceResponse(
        @SerializedName("currency_id") val currencyId: String,
        val amount: Double
    )
}

interface ExchangeRateApiService {
    @GET("live-rates")
    suspend fun getLiveRates(): List<LiveRateResponse>

    data class LiveRateResponse(
        @SerializedName("currency_id") val currencyId: String,
        @SerializedName("usd_rate") val usdRate: Double
    )
}