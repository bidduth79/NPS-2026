package com.example.data

object Rules {
    fun getAnnualIncrementRate(grade: Int): Double {
        return when (grade) {
            1 -> 0.0
            2 -> 0.0275
            3, 4 -> 0.035
            5 -> 0.04
            in 6..20 -> 0.05
            else -> 0.0
        }
    }

    fun getStage1Rate(grade: Int): Double {
        return if (grade in 1..9) 0.40 else 0.50
    }

    fun getStage2Rate(grade: Int): Double {
        return if (grade in 1..9) 0.70 else 0.75
    }
}
