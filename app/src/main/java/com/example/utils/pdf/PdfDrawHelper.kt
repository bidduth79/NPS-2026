package com.example.utils.pdf

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import com.example.utils.CalculationResult
import com.example.utils.NumberFormatter

class PdfDrawHelper(
    private val canvas: Canvas,
    private val paint: Paint,
    private val textPaint: Paint,
    private val normalFont: Typeface,
    private val boldFont: Typeface
) {

    fun drawCard(title: String, titleBgColor: Int, contentLines: List<Pair<String, String>>, yStart: Float): Float {
        val cardRect = RectF(30f, yStart, 565f, yStart + 35f + (contentLines.size * 22f) + 10f)
        
        paint.color = Color.parseColor("#F8FAFC") // Very light gray
        canvas.drawRoundRect(cardRect, 8f, 8f, paint)
        
        paint.style = Paint.Style.STROKE
        paint.color = Color.parseColor("#E2E8F0")
        paint.strokeWidth = 1f
        canvas.drawRoundRect(cardRect, 8f, 8f, paint)
        paint.style = Paint.Style.FILL

        val titleRect = RectF(30f, yStart, 565f, yStart + 30f)
        paint.color = titleBgColor
        canvas.drawRoundRect(titleRect, 8f, 8f, paint)
        canvas.drawRect(30f, yStart + 15f, 565f, yStart + 30f, paint)

        textPaint.color = if (titleBgColor == Color.parseColor("#F8FAFC") || titleBgColor == Color.parseColor("#F1F5F9")) Color.BLACK else Color.WHITE
        textPaint.typeface = boldFont
        textPaint.textSize = 10f
        canvas.drawText(title, 40f, yStart + 20f, textPaint)

        var textY = yStart + 50f
        for (line in contentLines) {
            textPaint.color = Color.DKGRAY
            textPaint.typeface = normalFont
            canvas.drawText(line.first, 40f, textY, textPaint)
            
            textPaint.color = Color.BLACK
            textPaint.typeface = boldFont
            textPaint.textAlign = Paint.Align.RIGHT
            canvas.drawText(line.second, 550f, textY, textPaint)
            textPaint.textAlign = Paint.Align.LEFT
            
            textY += 22f
        }
        return cardRect.bottom + 10f
    }

    fun drawDetailedStageCard(
        title: String, 
        titleBgColor: Int, 
        topBoxes: List<Triple<String, String, String>>, 
        rate: Double, 
        stageResult: Long, 
        yStart: Float,
        result: CalculationResult
    ): Float {
        val ratePercent = (rate * 100).toInt()
        val cardRect = RectF(30f, yStart, 565f, yStart + 225f)
        
        paint.color = Color.WHITE
        canvas.drawRoundRect(cardRect, 8f, 8f, paint)
        paint.style = Paint.Style.STROKE
        paint.color = Color.parseColor("#E2E8F0")
        canvas.drawRoundRect(cardRect, 8f, 8f, paint)
        paint.style = Paint.Style.FILL

        val titleRect = RectF(30f, yStart, 565f, yStart + 30f)
        paint.color = titleBgColor
        canvas.drawRoundRect(titleRect, 8f, 8f, paint)
        canvas.drawRect(30f, yStart + 15f, 565f, yStart + 30f, paint)

        textPaint.color = Color.WHITE
        textPaint.typeface = boldFont
        textPaint.textSize = 10f
        canvas.drawText(title, 40f, yStart + 20f, textPaint)

        // Top boxes
        val boxWidth = 535f / 3f
        var boxX = 30f
        for (i in 0..2) {
            if (i > 0) {
                paint.color = Color.parseColor("#E2E8F0")
                canvas.drawLine(boxX, yStart + 35f, boxX, yStart + 60f, paint)
            }
            textPaint.color = Color.GRAY
            textPaint.typeface = normalFont
            textPaint.textSize = 8f
            canvas.drawText(topBoxes[i].first, boxX + 10f, yStart + 45f, textPaint)
            
            textPaint.color = Color.BLACK
            textPaint.typeface = boldFont
            textPaint.textSize = 12f
            if (i == 2) textPaint.color = titleBgColor 
            canvas.drawText(topBoxes[i].second, boxX + 10f, yStart + 60f, textPaint)
            boxX += boxWidth
        }

        paint.color = Color.parseColor("#F1F5F9")
        canvas.drawRect(30f, yStart + 68f, 565f, yStart + 86f, paint)
        
        textPaint.color = Color.GRAY
        textPaint.typeface = normalFont
        textPaint.textSize = 8f
        canvas.drawText("STEP", 40f, yStart + 80f, textPaint)
        canvas.drawText("DESCRIPTION", 70f, yStart + 80f, textPaint)
        canvas.drawText("CALCULATION", 380f, yStart + 80f, textPaint)
        textPaint.textAlign = Paint.Align.RIGHT
        canvas.drawText("AMOUNT (৳)", 550f, yStart + 80f, textPaint)
        textPaint.textAlign = Paint.Align.LEFT

        // Rows
        val r = result.details
        val step6Value = (r.finalConvertedBasic - result.currentBasic) * rate
        val step6ValueLong = step6Value.toLong()
        val rows = listOf(
            arrayOf("1", "Old pay − Old minimum pay", "${result.currentBasic} − ${r.firstStep2015}", "${NumberFormatter.format(r.accruedIncrease)}"),
            arrayOf("2", "New minimum pay", "—", "${NumberFormatter.format(r.firstStep2026)}"),
            arrayOf("3", "New minimum + Step 1", "${r.firstStep2026} + ${r.accruedIncrease}", "${NumberFormatter.format(r.target)}"),
            arrayOf("4", "Matching / next higher stage on NPS 2026", "—", "${NumberFormatter.format(r.nextStep2026Target)}"),
            arrayOf("5", "Next stage after Step 4 (Fact Increment)", "—", "${NumberFormatter.format(r.finalConvertedBasic)}"),
            arrayOf("6", "(Step 5 − Old pay) × $ratePercent%", "(${r.finalConvertedBasic} − ${result.currentBasic}) × $ratePercent%", "${NumberFormatter.format(step6ValueLong)}"),
            arrayOf("7", "Old pay + Step 6 (new basic)", "${result.currentBasic} + $step6ValueLong", "${NumberFormatter.format(stageResult)}")
        )

        var rowY = yStart + 100f
        for (i in rows.indices) {
            textPaint.color = Color.BLACK
            textPaint.typeface = normalFont
            textPaint.textSize = 8f
            canvas.drawText(rows[i][0], 40f, rowY, textPaint)
            canvas.drawText(rows[i][1], 70f, rowY, textPaint)
            
            textPaint.color = Color.GRAY
            canvas.drawText(rows[i][2], 380f, rowY, textPaint)
            
            textPaint.color = Color.BLACK
            textPaint.typeface = boldFont
            textPaint.textAlign = Paint.Align.RIGHT
            canvas.drawText(rows[i][3], 550f, rowY, textPaint)
            textPaint.textAlign = Paint.Align.LEFT
            
            if (i < rows.size - 1) {
                paint.color = Color.parseColor("#F1F5F9")
                canvas.drawLine(30f, rowY + 5f, 565f, rowY + 5f, paint)
            }
            rowY += 17f
        }
        return cardRect.bottom + 10f
    }
}
