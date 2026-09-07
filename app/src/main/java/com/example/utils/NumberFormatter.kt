package com.example.utils

object NumberFormatter {
    fun format(number: Any): String {
        var str = number.toString()
        if (number is Number && number.toLong() >= 1000) {
            str = String.format("%,d", number.toLong())
        }
        return str
    }
}
