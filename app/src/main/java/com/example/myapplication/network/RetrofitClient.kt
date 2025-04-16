package com.example.myapplication.network

import android.content.Context
import com.example.myapplication.service.CurrencyApiService
import com.example.myapplication.service.ExchangeRateApiService
import com.example.myapplication.service.WalletApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


// Retrofit 客户端单例
class RetrofitClient private constructor(context: Context) {
    private val BASE_URL = "https://api.crypto.com/"

    // 缓存实例
    companion object {
        @Volatile
        private var INSTANCE: RetrofitClient? = null

        fun getInstance(context: Context): RetrofitClient {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: RetrofitClient(context).also { INSTANCE = it }
            }
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(MockInterceptor(context))
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
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