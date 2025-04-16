package com.example.myapplication.network

import android.content.Context
import androidx.annotation.RawRes
import com.example.myapplication.R
import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException


class MockInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val path = chain.request().url.encodedPath
        val mockResponse = when {
            path.contains("/currencies/list") -> getMockResponseFromRaw(context, R.raw.currency)
            path.contains("/live/rates") -> getMockResponseFromRaw(context, R.raw.live_rates)
            path.contains("/wallet/balances") -> getMockResponseFromRaw(context, R.raw.wallet_balance)
            else -> """{"error": "No mock data found for $path"}"""
        }

        return Response.Builder().code(200).message(mockResponse ?: "").request(chain.request()).protocol(Protocol.HTTP_1_1)
            .body(mockResponse?.toByteArray()?.toResponseBody("application/json".toMediaType())).addHeader("content-type", "application/json").build()
    }
}

fun getMockResponseFromRaw(context: Context, @RawRes resId: Int): String? {
    return try {
        context.resources.openRawResource(resId).bufferedReader().use { it.readText() }
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}