package com.example.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class SalaryCalculatorTest {

    @Test
    fun testGrade16Step16() {
        val result = SalaryCalculator.calculate(16, 15) // Step 16 is index 15
        requireNotNull(result)
        assertEquals(34040, result.convertedBasic)
        assertEquals(26725, result.stage1)
        assertEquals(30383, result.stage2)
        assertEquals(34040, result.stage3)
    }

    @Test
    fun testGrade16Step17() {
        val result = SalaryCalculator.calculate(16, 16) // Step 17 is index 16
        requireNotNull(result)
        assertEquals(35750, result.convertedBasic)
        assertEquals(28070, result.stage1)
        assertEquals(31910, result.stage2)
        assertEquals(35750, result.stage3)
    }

    @Test
    fun testGrade10Step17() {
        val result = SalaryCalculator.calculate(10, 16) // Step 17 is index 16
        requireNotNull(result)
        assertEquals(54860, result.convertedBasic)
        assertEquals(44950, result.stage1)
        assertEquals(49905, result.stage2) // Note: 35040 + (54860 - 35040)*0.75 = 35040 + 14865 = 49905. The prompt says 49905, we verify this match.
        assertEquals(54860, result.stage3)
    }

    @Test
    fun testGrade3Step2() {
        val result = SalaryCalculator.calculate(3, 1) // Step 2 is index 1
        requireNotNull(result)
        assertEquals(122240, result.convertedBasic)
        assertEquals(84152, result.stage1)
        assertEquals(103196, result.stage2) // 58760 + 63480 * 0.70 = 103196
        assertEquals(122240, result.stage3)
    }
}
