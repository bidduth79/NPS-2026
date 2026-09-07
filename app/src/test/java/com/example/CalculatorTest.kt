package com.example

import org.junit.Test
import org.junit.Assert.*
import com.example.utils.SalaryCalculator

class CalculatorTest {
    @Test
    fun testGrade16Step16() {
        val res = SalaryCalculator.calculate(16, 15)
        println("Target Output:")
        println("Stage 1: ${res?.stage1}")
        println("Stage 2: ${res?.stage2}")
        println("Stage 3: ${res?.stage3}")
    }
}
