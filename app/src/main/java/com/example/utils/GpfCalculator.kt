package com.example.utils

import kotlin.math.roundToLong

data class GpfMonthResult(
    val monthName: String,
    val openingBalance: Long,
    val deposit: Long,
    val profit: Long,
    val closingBalance: Long
)

data class GpfYearResult(
    val fiscalYear: String,
    val openingBalance: Long,
    val monthlySubscription: Long,
    val totalSubscription: Long,
    val profitRate: Double,
    val profit: Long,
    val closingBalance: Long,
    val monthlyDetails: List<GpfMonthResult> = emptyList()
)

object GpfCalculator {
    fun calculate(
        startYear: Int,
        endYear: Int,
        initialBalance: Long,
        monthlySubscription: Long,
        baseRate: Double? = null
    ): List<GpfYearResult> {
        val results = mutableListOf<GpfYearResult>()
        var currentBalance = initialBalance
        
        val months = listOf("Jul", "Aug", "Sep", "Oct", "Nov", "Dec", "Jan", "Feb", "Mar", "Apr", "May", "Jun")

        for (year in startYear until endYear) {
            val fiscalYear = "$year-${(year + 1).toString().takeLast(2)}"
            
            val rate = baseRate ?: when {
                currentBalance <= 1500000 -> 0.13
                currentBalance <= 3000000 -> 0.12
                else -> 0.11
            }

            val totalSubscription = monthlySubscription * 12
            val profitOnOpening = currentBalance * rate
            val profitOnDeposits = monthlySubscription * rate * 6.5
            val totalProfit = (profitOnOpening + profitOnDeposits).roundToLong()
            val closingBalance = currentBalance + totalSubscription + totalProfit

            val monthlyDetails = mutableListOf<GpfMonthResult>()
            var monthBalance = currentBalance
            
            for (i in 0..11) {
                val isJune = (i == 11)
                val mProfit = if (isJune) totalProfit else 0L
                val mClosing = monthBalance + monthlySubscription + mProfit
                monthlyDetails.add(
                    GpfMonthResult(
                        monthName = months[i],
                        openingBalance = monthBalance,
                        deposit = monthlySubscription,
                        profit = mProfit,
                        closingBalance = mClosing
                    )
                )
                monthBalance = mClosing
            }

            results.add(
                GpfYearResult(
                    fiscalYear = fiscalYear,
                    openingBalance = currentBalance,
                    monthlySubscription = monthlySubscription,
                    totalSubscription = totalSubscription,
                    profitRate = rate,
                    profit = totalProfit,
                    closingBalance = closingBalance,
                    monthlyDetails = monthlyDetails
                )
            )

            currentBalance = closingBalance
        }

        return results
    }
}
