package com.example.utils.pdf

import android.graphics.Canvas
import android.graphics.Paint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object GpfPdfHeaderHelper {
    fun drawHeader(
        canvas: Canvas,
        paint: Paint,
        textPaint: Paint,
        boldPaint: Paint,
        startX: Float
    ): Float {
        var currentY = 50f
        val pageCenter = 595f / 2f
        
        boldPaint.textSize = 14f
        boldPaint.textAlign = Paint.Align.CENTER
        canvas.drawText("Government of the People's Republic of Bangladesh", pageCenter, currentY, boldPaint)
        
        currentY += 20f
        boldPaint.textSize = 12f
        canvas.drawText("Office of the Chief Accounts and Finance Officer", pageCenter, currentY, boldPaint)
        
        currentY += 20f
        textPaint.textSize = 10f
        textPaint.textAlign = Paint.Align.CENTER
        canvas.drawText("Ministry of Land", pageCenter, currentY, textPaint)
        
        currentY += 15f
        canvas.drawText("Hisab Bhaban, Segunbagicha, Dhaka", pageCenter, currentY, textPaint)
        
        currentY += 30f
        boldPaint.textSize = 12f
        canvas.drawText("GPF Account Slip", pageCenter, currentY, boldPaint)
        
        currentY += 15f
        canvas.drawText("For the year: 2024-25", pageCenter, currentY, boldPaint)
        
        currentY += 30f
        textPaint.textAlign = Paint.Align.LEFT
        textPaint.textSize = 10f
        
        val dateStr = SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(Date())
        canvas.drawText("Date: $dateStr", 450f, currentY, textPaint)
        
        canvas.drawText("To:", startX, currentY, textPaint)
        currentY += 15f
        boldPaint.textSize = 11f
        boldPaint.textAlign = Paint.Align.LEFT
        canvas.drawText("[Name not provided]", startX, currentY, boldPaint)
        
        currentY += 15f
        textPaint.textSize = 10f
        canvas.drawText("Designation: [Not provided]", startX, currentY, textPaint)
        
        currentY += 15f
        canvas.drawText("Office: (Not Provided)", startX, currentY, textPaint)
        
        currentY += 25f
        canvas.drawText("Sir/Madam,", startX, currentY, textPaint)
        currentY += 15f
        canvas.drawText("I am to inform you that your General Provident Fund (GPF) Account number is as follows:", startX, currentY, textPaint)
        
        return currentY + 20f
    }
}
