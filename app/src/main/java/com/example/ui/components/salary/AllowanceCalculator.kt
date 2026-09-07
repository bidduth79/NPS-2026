package com.example.ui.components.salary

import com.example.data.PayScale2015
import com.example.data.PayScale2026
import com.example.data.UserProfile

fun calculateDetailedAllowances(basic: Long, selectedGrade: Int, profile: UserProfile, selectedStepIndex: Int, locDhaka: String, locOtherCity: String): Map<String, Long> {
    val is2026Base = profile.selectedScale == "2026"

    val medicalAllowance = if (is2026Base) 3000L else 1500L
    val educationAllowance = if (is2026Base) {
        (profile.numberOfChildren * 1000L).coerceAtMost(2000L)
    } else {
        (profile.numberOfChildren * 500L).coerceAtMost(1000L) // Max 2 children
    }
    
    val tiffinAllowance = if (profile.hasTiffinAllowance) (if (is2026Base) 600L else 200L) else 0L
    val washingAllowance = if (profile.hasWashingAllowance) 300L else 0L
    
    // Base for allowance calculation
    val baseForAllowance = if (is2026Base) {
        PayScale2026.steps[selectedGrade]?.let { steps2026 ->
            val targetStep = selectedStepIndex.coerceAtMost(steps2026.size - 1)
            steps2026[targetStep].toLong()
        } ?: basic
    } else {
        PayScale2015.steps[selectedGrade]?.let { steps2015 ->
            val targetStep = (selectedStepIndex + 1).coerceAtMost(steps2015.size - 1)
            steps2015[targetStep].toLong()
        } ?: basic
    }

    val houseRent = calculateHouseRent(profile, baseForAllowance, locDhaka, locOtherCity)
    val hillAllowance = if (profile.hasHillAllowance) {
        val maxLimit = if (profile.hillAllowanceAreaType == "Sadar") 3000L else 5000L
        (baseForAllowance * 0.20).toLong().coerceAtMost(maxLimit)
    } else {
        0L
    }
    val disabledChildAllowance = profile.numberOfDisabledChildren * (if (is2026Base) 1000L else 1000L) // As per image it says 1000 Tk normally, but let's stick to 1000
    val frontierAllowance = calculateFrontierAllowance(profile)
    
    // Annual/Periodic (represented here for record if needed, but not part of monthly gross usually unless calculated, we return them separately)
    val festivalAllowance = if (profile.hasFestivalAllowance) baseForAllowance * 2 else 0L // 2 bonuses yearly
    val baishakhiAllowance = if (profile.hasBaishakhiAllowance) (baseForAllowance * 0.2).toLong() else 0L
    
    val totalGross = basic + houseRent + medicalAllowance + educationAllowance + tiffinAllowance + washingAllowance + hillAllowance + frontierAllowance + disabledChildAllowance + profile.mobileBillAmount + profile.tradeAllowanceAmount
    
    return mapOf(
        "houseRent" to houseRent,
        "medicalAllowance" to medicalAllowance,
        "educationAllowance" to educationAllowance,
        "tiffinAllowance" to tiffinAllowance,
        "washingAllowance" to washingAllowance,
        "hillAllowance" to hillAllowance,
        "frontierAllowance" to frontierAllowance,
        "disabledChildAllowance" to disabledChildAllowance,
        "mobileBill" to profile.mobileBillAmount,
        "tradeAllowance" to profile.tradeAllowanceAmount,
        "totalGross" to totalGross,
        "festivalAllowance" to festivalAllowance,
        "baishakhiAllowance" to baishakhiAllowance,
        "recreationAllowance" to profile.recreationAllowanceAmount,
        "awardAllowance" to profile.awardAllowanceAmount
    )
}

private fun calculateHouseRent(profile: UserProfile, baseForAllowance: Long, locDhaka: String, locOtherCity: String): Long {
    // Unmarried condition: 30% everywhere
    if (profile.maritalStatus == "Unmarried") {
        return (baseForAllowance * 0.30).toLong()
    }

    // Married condition but Line Man or In-Living: 50% everywhere
    if (profile.isLineMan || profile.isInLiving) {
        val minFloor = when {
            baseForAllowance <= 9700 -> 4500L
            baseForAllowance <= 16000 -> 4800L
            baseForAllowance <= 35400 -> 7000L
            else -> 12600L
        }
        return (baseForAllowance * 0.50).toLong().coerceAtLeast(minFloor)
    }

    // Married, Family Man, Out-Living (Location Based)
    return when (profile.locationType) {
        locDhaka, "Dhaka Metropolitan", "ঢাকা মেট্রোপলিটন" -> {
            val rate = when {
                baseForAllowance <= 9700 -> 0.65
                baseForAllowance <= 16000 -> 0.60
                baseForAllowance <= 35400 -> 0.55
                else -> 0.50
            }
            val min = when {
                baseForAllowance <= 9700 -> 5600L
                baseForAllowance <= 16000 -> 6400L
                baseForAllowance <= 35400 -> 9600L
                else -> 19500L
            }
            (baseForAllowance * rate).toLong().coerceAtLeast(min)
        }
        locOtherCity, "Other City Corporation", "অন্যান্য সিটি কর্পোরেশন" -> {
            val rate = when {
                baseForAllowance <= 9700 -> 0.55
                baseForAllowance <= 16000 -> 0.50
                baseForAllowance <= 35400 -> 0.45
                else -> 0.40
            }
            val min = when {
                baseForAllowance <= 9700 -> 5000L
                baseForAllowance <= 16000 -> 5400L
                baseForAllowance <= 35400 -> 8000L
                else -> 16000L
            }
            (baseForAllowance * rate).toLong().coerceAtLeast(min)
        }
        else -> {
            val rate = when {
                baseForAllowance <= 9700 -> 0.50
                baseForAllowance <= 16000 -> 0.45
                baseForAllowance <= 35400 -> 0.40
                else -> 0.35
            }
            val min = when {
                baseForAllowance <= 9700 -> 4500L
                baseForAllowance <= 16000 -> 4800L
                baseForAllowance <= 35400 -> 7000L
                else -> 12600L
            }
            (baseForAllowance * rate).toLong().coerceAtLeast(min)
        }
    }
}

private fun calculateFrontierAllowance(profile: UserProfile): Long {
    if (!profile.hasFrontierAllowance) return 0L
    
    val dateStr = profile.joiningDate.trim()
    if (dateStr.isEmpty()) return 1200L
    
    // Convert Bengali digits to English just in case
    val englishDateStr = dateStr.map { char ->
        when (char) {
            '০' -> '0'; '১' -> '1'; '২' -> '2'; '৩' -> '3'; '৪' -> '4'
            '৫' -> '5'; '৬' -> '6'; '৭' -> '7'; '৮' -> '8'; '৯' -> '9'
            else -> char
        }
    }.joinToString("")

    val parts = englishDateStr.split(Regex("[^0-9]")).filter { it.isNotEmpty() }
    if (parts.size < 3) return 1200L // Default if missing or invalid date

    try {
        val day = parts[0].toIntOrNull() ?: 1
        val month = parts[1].toIntOrNull() ?: 1
        val year = parts[2].let {
            if (it.length == 2) {
                val y = it.toInt()
                if (y > 50) 1900 + y else 2000 + y
            } else it.toIntOrNull() ?: 2024
        }
        
        val calendar = java.util.Calendar.getInstance()
        val currentYear = calendar.get(java.util.Calendar.YEAR)
        val currentMonth = calendar.get(java.util.Calendar.MONTH) + 1
        val currentDay = calendar.get(java.util.Calendar.DAY_OF_MONTH)
        
        var jobYears = currentYear - year
        if (currentMonth < month || (currentMonth == month && currentDay < day)) {
            jobYears--
        }
        
        return when {
            jobYears < 6 -> 1200L
            jobYears in 6..12 -> 1500L
            jobYears in 13..15 -> 1800L
            jobYears in 16..18 -> 2200L
            jobYears in 19..22 -> 2800L
            else -> 3000L
        }
    } catch (e: Exception) {
        return 1200L
    }
}
