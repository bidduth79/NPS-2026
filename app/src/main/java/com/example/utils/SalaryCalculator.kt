package com.example.utils

import com.example.data.PayScale2015
import com.example.data.PayScale2026
import com.example.data.Rules
import kotlin.math.ceil

data class CalculationResult(
    val grade: Int,
    val currentBasic: Long,
    val convertedBasic: Long,
    val stage1: Long,
    val stage2: Long,
    val stage3: Long,
    val details: CalculationDetails
)

data class CalculationDetails(
    val basic2015: Long,
    val firstStep2015: Long,
    val accruedIncrease: Long,
    val firstStep2026: Long,
    val target: Long,
    val nextStep2026Target: Long,
    val annualIncrement: Double,
    val totalBeforeFinalTarget: Double,
    val finalConvertedBasic: Long,
    val totalIncrease: Long
)

object SalaryCalculator {
    fun calculate(grade: Int, stepIndex: Int): CalculationResult? {
        val steps2015 = PayScale2015.steps[grade] ?: return null
        val steps2026 = PayScale2026.steps[grade] ?: return null
        
        if (stepIndex !in steps2015.indices) return null

        val currentBasic = steps2015[stepIndex].toLong()
        val firstStep2015 = steps2015.first().toLong()
        val accruedIncrease = currentBasic - firstStep2015
        
        val firstStep2026 = steps2026.first().toLong()
        val target = firstStep2026 + accruedIncrease
        
        // Find next higher or equal step in 2026 for target
        val nextStep2026Target = findNextHigherStep(target, steps2026)
        
        val annualIncrementRate = Rules.getAnnualIncrementRate(grade)
        val annualIncrement = nextStep2026Target * annualIncrementRate
        val totalBeforeFinalTarget = nextStep2026Target + annualIncrement
        
        // Find next higher or equal step for the incremented target
        val finalConvertedBasic = if (grade == 1) {
            steps2026.first().toLong() // Fixed
        } else {
            findNextHigherStep(totalBeforeFinalTarget, steps2026)
        }
        
        val totalIncrease = finalConvertedBasic - currentBasic
        
        val stage1Increase = ceil(totalIncrease * Rules.getStage1Rate(grade)).toLong()
        val stage1 = currentBasic + stage1Increase
        
        val stage2Increase = ceil(totalIncrease * Rules.getStage2Rate(grade)).toLong()
        val stage2 = currentBasic + stage2Increase
        
        // Stage 3 is the NEXT stage after the finalConvertedBasic (01-07-2027)
        val finalConvertedIndex = steps2026.indexOf(finalConvertedBasic.toInt())
        val stage3 = if (finalConvertedIndex != -1 && finalConvertedIndex + 1 < steps2026.size) {
            steps2026[finalConvertedIndex + 1].toLong()
        } else {
            finalConvertedBasic // If already at highest step, it stays the same
        }

        return CalculationResult(
            grade = grade,
            currentBasic = currentBasic,
            convertedBasic = finalConvertedBasic,
            stage1 = stage1,
            stage2 = stage2,
            stage3 = stage3,
            details = CalculationDetails(
                basic2015 = currentBasic,
                firstStep2015 = firstStep2015,
                accruedIncrease = accruedIncrease,
                firstStep2026 = firstStep2026,
                target = target,
                nextStep2026Target = nextStep2026Target,
                annualIncrement = annualIncrement,
                totalBeforeFinalTarget = totalBeforeFinalTarget,
                finalConvertedBasic = finalConvertedBasic,
                totalIncrease = totalIncrease
            )
        )
    }

    private fun findNextHigherStep(target: Number, steps: List<Int>): Long {
        val targetDouble = target.toDouble()
        // Allow tiny tolerance for floating point matching
        val match = steps.firstOrNull { it.toDouble() >= targetDouble - 0.01 }
        return match?.toLong() ?: steps.last().toLong()
    }
}
