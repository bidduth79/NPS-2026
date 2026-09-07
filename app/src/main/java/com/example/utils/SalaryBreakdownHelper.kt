package com.example.utils

import com.example.data.UserProfile

fun calculateSalaryBreakdown(result: CalculationResult, profile: UserProfile): Map<String, Long> {
    val basic = result.convertedBasic
    val baseForAllowance = if (profile.allowanceBaseScale == "2026") basic else result.currentBasic
    
    val houseRentRate = when (profile.locationType) {
        "Dhaka Metropolitan", "ঢাকা মেট্রোপলিটন" -> if (baseForAllowance <= 9700) 0.65 else if (baseForAllowance <= 16000) 0.60 else if (baseForAllowance <= 35400) 0.55 else 0.50
        "Other City Corporation", "অন্যান্য সিটি কর্পোরেশন" -> if (baseForAllowance <= 9700) 0.55 else if (baseForAllowance <= 16000) 0.50 else if (baseForAllowance <= 35400) 0.45 else 0.40
        else -> if (baseForAllowance <= 9700) 0.50 else if (baseForAllowance <= 16000) 0.45 else if (baseForAllowance <= 35400) 0.40 else 0.35
    }
    val minimumHouseRent = when (profile.locationType) {
        "Dhaka Metropolitan", "ঢাকা মেট্রোপলিটন" -> if (baseForAllowance <= 9700) 5600L else if (baseForAllowance <= 16000) 6400L else if (baseForAllowance <= 35400) 9600L else 19500L
        "Other City Corporation", "অন্যান্য সিটি কর্পোরেশন" -> if (baseForAllowance <= 9700) 5000L else if (baseForAllowance <= 16000) 5400L else if (baseForAllowance <= 35400) 8000L else 16000L
        else -> if (baseForAllowance <= 9700) 4500L else if (baseForAllowance <= 16000) 4800L else if (baseForAllowance <= 35400) 7000L else 12600L
    }
    val houseRent = (baseForAllowance * houseRentRate).toLong().coerceAtLeast(minimumHouseRent)
    
    val medicalAllowance = if (profile.allowanceBaseScale == "2026") 3000L else 1500L
    val educationAllowance = profile.numberOfChildren * (if (profile.allowanceBaseScale == "2026") 1000L else 500L)
    
    val otherAllowances = (if (profile.hasTiffinAllowance) (if (profile.allowanceBaseScale == "2026") 600L else 200L) else 0L) +
        (if (profile.hasWashingAllowance) 150L else 0L) +
        ((baseForAllowance * profile.hillAllowancePercent / 100).toLong()) +
        (profile.numberOfDisabledChildren * 3000L) +
        profile.mobileBillAmount + profile.tradeAllowanceAmount
        
    val totalGross = basic + houseRent + medicalAllowance + educationAllowance + otherAllowances
    val gpf = profile.gpfDeduction
    val net = totalGross - gpf
    
    return mapOf(
        "basic" to basic,
        "houseRent" to houseRent,
        "medical" to medicalAllowance,
        "education" to educationAllowance,
        "others" to otherAllowances,
        "totalGross" to totalGross,
        "gpf" to gpf,
        "net" to net
    )
}
