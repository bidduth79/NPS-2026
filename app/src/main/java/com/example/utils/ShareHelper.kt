package com.example.utils

import android.content.Context
import android.content.Intent

object ShareHelper {
    fun shareResult(context: Context, result: CalculationResult) {
        val text = """
            Pay Scale 2026 - Salary Calculation
            
            Grade: ${result.grade}
            Old Basic: ৳${NumberFormatter.format(result.currentBasic)}
            
            Stage-1 (Jul 2026): ৳${NumberFormatter.format(result.stage1)}
            Stage-2 (Jan 2027): ৳${NumberFormatter.format(result.stage2)}
            Converted Basic (2026): ৳${NumberFormatter.format(result.convertedBasic)}
            
            Generated via PayScale 2026 App
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        context.startActivity(Intent.createChooser(intent, "Share via"))
    }
}
