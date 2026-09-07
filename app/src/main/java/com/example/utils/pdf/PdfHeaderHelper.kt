package com.example.utils.pdf

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object PdfHeaderHelper {
    fun drawHeader(
        canvas: Canvas,
        paint: Paint,
        textPaint: Paint,
        gradeIndex: Int,
        stepIndex: Int,
        stageName: String
    ): Float {
        val primaryPurple = Color.parseColor("#4C1D95")
        val white = Color.WHITE

        // --- Draw Header Background ---
        paint.color = primaryPurple
        paint.style = Paint.Style.FILL
        canvas.drawRect(0f, 0f, 595f, 150f, paint)

        // --- Draw Header Text ---
        textPaint.color = white
        textPaint.textAlign = Paint.Align.CENTER
        
        // Title
        textPaint.textSize = 28f
        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("SALARY STATEMENT", 297.5f, 50f, textPaint)
        
        // Subtitle
        textPaint.textSize = 14f
        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        canvas.drawText("Detailed Salary Breakdown & Allowances", 297.5f, 80f, textPaint)
        
        // Grade & Step info
        val gradeText = "Grade ${gradeIndex + 1}"
        val stepText = "Step ${stepIndex + 1}"
        val dateStr = SimpleDateFormat("MMMM yyyy", Locale.US).format(Date())
        textPaint.textSize = 12f
        canvas.drawText("$gradeText | $stepText | $stageName - $dateStr", 297.5f, 110f, textPaint)

        return 180f // Returns the Y position for the next element (tableTop)
    }
}
