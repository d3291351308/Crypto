package com.example.myapplication.network

import com.example.myapplication.service.CurrencyApiService
import com.example.myapplication.service.ExchangeRateApiService
import com.example.myapplication.service.WalletApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// Retrofit 客户端单例
object RetrofitClient {
    private const val BASE_URL = "https://api.crypto.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val currencyService: CurrencyApiService by lazy {
        retrofit.create(CurrencyApiService::class.java)
    }

    val walletService: WalletApiService by lazy {
        retrofit.create(WalletApiService::class.java)
    }

    val rateService: ExchangeRateApiService by lazy {
        retrofit.create(ExchangeRateApiService::class.java)
    }
}