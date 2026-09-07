package com.example.utils.pdf

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import com.example.utils.GpfYearResult
import com.example.utils.NumberFormatter

object GpfPdfTableHelper {
    
    fun drawTableHeaders(canvas: Canvas, paint: Paint, textPaint: Paint, currentY: Float): Float {
        var startY = currentY
        val w = floatArrayOf(55f, 95f, 65f, 50f, 35f, 50f, 55f, 55f, 55f)
        val headerHeight = 40f
        
        canvas.drawRect(40f, startY, 555f, startY + headerHeight, paint)
        
        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        textPaint.textSize = 8f
        textPaint.textAlign = Paint.Align.CENTER
        
        var currentX = 40f
        for (i in w.indices) {
            if (i > 0) canvas.drawLine(currentX, startY, currentX, startY + headerHeight, paint)
            
            val midX = currentX + (w[i] / 2f)
            when (i) {
                0 -> drawMultilineText(canvas, "Account No.", midX, startY + 15f, textPaint)
                1 -> drawMultilineText(canvas, "Name Of Subscriber", midX, startY + 15f, textPaint)
                2 -> drawMultilineText(canvas, "Opening\nbalance", midX, startY + 15f, textPaint)
                6 -> drawMultilineText(canvas, "Profit for\nthe year", midX, startY + 15f, textPaint)
                7 -> drawMultilineText(canvas, "Withdrawal(s)\nduring the year", midX, startY + 15f, textPaint)
                8 -> drawMultilineText(canvas, "Closing\nbalance", midX, startY + 15f, textPaint)
            }
            currentX += w[i]
        }
        
        val depStartX = 40f + w[0] + w[1] + w[2]
        val depWidth = w[3] + w[4] + w[5]
        canvas.drawLine(depStartX, startY + 20f, depStartX + depWidth, startY + 20f, paint)
        canvas.drawText("*Deposits during the year", depStartX + (depWidth / 2f), startY + 12f, textPaint)
        
        canvas.drawText("Subscription", depStartX + (w[3] / 2f), startY + 32f, textPaint)
        canvas.drawText("Refund", depStartX + w[3] + (w[4] / 2f), startY + 32f, textPaint)
        canvas.drawText("Total", depStartX + w[3] + w[4] + (w[5] / 2f), startY + 32f, textPaint)
        
        startY += headerHeight
        
        val numRowHeight = 15f
        canvas.drawRect(40f, startY, 555f, startY + numRowHeight, paint)
        currentX = 40f
        for (i in w.indices) {
            if (i > 0) canvas.drawLine(currentX, startY, currentX, startY + numRowHeight, paint)
            canvas.drawText((i + 1).toString(), currentX + (w[i] / 2f), startY + 11f, textPaint)
            currentX += w[i]
        }
        
        return startY + numRowHeight
    }
    
    fun drawTableRows(canvas: Canvas, paint: Paint, textPaint: Paint, results: List<GpfYearResult>, startY: Float): Float {
        var currentY = startY
        val w = floatArrayOf(55f, 95f, 65f, 50f, 35f, 50f, 55f, 55f, 55f)
        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        
        for (result in results) {
            val rowHeight = 25f
            canvas.drawRect(40f, currentY, 555f, currentY + rowHeight, paint)
            
            var currentX = 40f
            for (i in w.indices) {
                if (i > 0) canvas.drawLine(currentX, currentY, currentX, currentY + rowHeight, paint)
                val midX = currentX + (w[i] / 2f)
                
                val text = when (i) {
                    0 -> "[Blank]"
                    1 -> "[Blank]"
                    2 -> NumberFormatter.format(result.openingBalance)
                    3 -> NumberFormatter.format(result.totalSubscription)
                    4 -> "0"
                    5 -> NumberFormatter.format(result.totalSubscription)
                    6 -> NumberFormatter.format(result.profit)
                    7 -> "0"
                    8 -> NumberFormatter.format(result.closingBalance)
                    else -> ""
                }
                
                if (i == 0 || i == 1) {
                    textPaint.textSize = 7f
                } else {
                    textPaint.textSize = 8f
                }
                
                canvas.drawText(text, midX, currentY + 15f, textPaint)
                currentX += w[i]
            }
            currentY += rowHeight
        }
        return currentY
    }
    
    fun drawNotes(canvas: Canvas, paint: Paint, textPaint: Paint, boldPaint: Paint, startY: Float): Float {
        var currentY = startY
        textPaint.textAlign = Paint.Align.LEFT
        textPaint.textSize = 8f
        canvas.drawText("*Includes deposits made during the months of July to June.", 40f, currentY + 12f, textPaint)
        
        currentY += 40f
        boldPaint.textSize = 10f
        canvas.drawRect(40f, currentY - 10f, 75f, currentY + 2f, paint)
        canvas.drawText("Notes:", 42f, currentY, boldPaint)
        
        currentY += 20f
        textPaint.textSize = 9f
        drawMultilineTextLeft(canvas, "1. The subscriber is requested to state whether he desires to make any alteration in any nomination made under the rules of the fund.", 40f, currentY, 515f, textPaint)
        
        currentY += 30f
        drawMultilineTextLeft(canvas, "2. In case where the subscriber has made no nomination in favour of a member of his family owning to his having no family at the time, but acquired a family thereafter, the fact should be reported to the Account Officer forthwith.", 40f, currentY, 515f, textPaint)
        
        currentY += 30f
        drawMultilineTextLeft(canvas, "3. The subscriber is requested to satisfy himself/herself as to the correctness of the statement and to bring errors, if any, to the notice of the Account officer within three month(s) from the date of its receipt.", 40f, currentY, 515f, textPaint)
        
        currentY += 50f
        canvas.drawRect(40f, currentY - 10f, 210f, currentY + 2f, paint)
        canvas.drawText("[This GPF Account is - APPROVED]", 42f, currentY, boldPaint)
        
        currentY += 50f
        textPaint.textAlign = Paint.Align.CENTER
        canvas.drawText("[This is an iBAS++ system generated report, no signature is required]", 297f, currentY, textPaint)
        
        return currentY
    }

    private fun drawMultilineText(canvas: Canvas, text: String, x: Float, y: Float, paint: Paint) {
        val lines = text.split("\n")
        var currentY = y
        for (line in lines) {
            canvas.drawText(line, x, currentY, paint)
            currentY += paint.textSize + 2f
        }
    }

    private fun drawMultilineTextLeft(canvas: Canvas, text: String, x: Float, y: Float, maxWidth: Float, paint: Paint) {
        val words = text.split(" ")
        var currentLine = ""
        var currentY = y
        
        for (word in words) {
            val testLine = if (currentLine.isEmpty()) word else "$currentLine $word"
            val width = paint.measureText(testLine)
            
            if (width > maxWidth) {
                canvas.drawText(currentLine, x, currentY, paint)
                currentLine = word
                currentY += paint.textSize + 4f
            } else {
                currentLine = testLine
            }
        }
        if (currentLine.isNotEmpty()) {
            canvas.drawText(currentLine, x, currentY, paint)
        }
    }
}
