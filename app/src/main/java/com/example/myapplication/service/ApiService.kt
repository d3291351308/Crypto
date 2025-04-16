package com.example.myapplication.service

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET

interface CurrencyApiService {
    @GET("currencies/list")
    suspend fun getWalletCurrencies(): WalletCurrencyResponse

    data class WalletCurrencyResponse(
        val currencies: List<WalletCurrencyItem>
    )

    data class WalletCurrencyItem(
            val coin_id: String?,
            val name: String?,
            val symbol: String?,
            @SerializedName("colorful_image_url") val imageUrl: String?
    )
}

interface WalletApiService {
    @GET("wallet/balances")
    suspend fun getWalletBalances(): WalletBalanceResponse

    data class WalletBalanceResponse(
        val wallet: List<CurrencyBalanceItem>?
    )

    data class CurrencyBalanceItem(
            @SerializedName("currency") val currencyId: String?,
            val amount: String?
    )
}

interface ExchangeRateApiService {
    @GET("live/rates")
    suspend fun getLiveRates(): LiveRateResponse

    data class LiveRateResponse(
        val tiers: List<LiveRate>
    )

    data class LiveRate(
            @SerializedName("from_currency") val currencyId: String?,
            val rates: List<Rate>?
    )

    data class Rate(
        val amount: String?,
        val rate: String?
    )
}