package com.example.myapplication.util

import java.text.DecimalFormat

/**
 * Created by dan05.tang on 2025/4/16
 */
class DecimalFormatUtil {
    fun formatDecimal(number: Double): String {

        val decimalFormat = DecimalFormat("#.########")
        return decimalFormat.format(number)
    }
}