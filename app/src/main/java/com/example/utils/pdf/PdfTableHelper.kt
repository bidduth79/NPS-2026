package com.example.utils.pdf

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import com.example.data.UserProfile

object PdfTableHelper {
    fun drawTableAndSummary(
        canvas: Canvas,
        paint: Paint,
        textPaint: Paint,
        basic: Long,
        profile: UserProfile,
        allowances: Map<String, Long>,
        tableTop: Float
    ) {
        val lightPurple = Color.parseColor("#9333EA")
        val ultraLightPurple = Color.parseColor("#F3E8FF")
        val textColor = Color.parseColor("#1F2937")
        val white = Color.WHITE
        val red = Color.parseColor("#DC2626")
        val primaryPurple = Color.parseColor("#4C1D95")
        val borderPurple = Color.parseColor("#C084FC")
        
        val tableLeft = 40f
        val tableRight = 555f
        
        paint.color = ultraLightPurple
        paint.style = Paint.Style.FILL
        // We will draw row backgrounds individually
        
        paint.color = borderPurple
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1f
        
        // Table Header
        paint.style = Paint.Style.FILL
        paint.color = lightPurple
        canvas.drawRoundRect(tableLeft, tableTop, tableRight, tableTop + 40f, 8f, 8f, paint)
        
        textPaint.color = white
        textPaint.textAlign = Paint.Align.LEFT
        textPaint.textSize = 14f
        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        canvas.drawText("Description", tableLeft + 20f, tableTop + 25f, textPaint)
        
        textPaint.textAlign = Paint.Align.RIGHT
        canvas.drawText("Amount", tableRight - 20f, tableTop + 25f, textPaint)
        
        // Table Content
        var currentY = tableTop + 40f
        var isAlternate = true
        
        val items = mutableListOf<Pair<String, Long>>()
        items.add("Basic Salary" to basic)
        items.add("House Rent" to (allowances["houseRent"] ?: 0L))
        items.add("Medical Allowance" to (allowances["medicalAllowance"] ?: 0L))
        
        val edu = allowances["educationAllowance"] ?: 0L
        if (edu > 0) items.add("Education Allowance" to edu)
        
        val tiffin = allowances["tiffinAllowance"] ?: 0L
        if (tiffin > 0) items.add("Tiffin Allowance" to tiffin)
        
        val washing = allowances["washingAllowance"] ?: 0L
        if (washing > 0) items.add("Washing Allowance" to washing)
        
        val frontier = allowances["frontierAllowance"] ?: 0L
        if (frontier > 0) items.add("Shimanto Allowance" to frontier)
        
        val hill = allowances["hillAllowance"] ?: 0L
        if (hill > 0) items.add("Hill Allowance" to hill)
        
        val disabled = allowances["disabledChildAllowance"] ?: 0L
        if (disabled > 0) items.add("Disabled Child Allowance" to disabled)
        
        val mobile = allowances["mobileBill"] ?: 0L
        if (mobile > 0) items.add("Mobile Bill" to mobile)
        
        val trade = allowances["tradeAllowance"] ?: 0L
        if (trade > 0) items.add("Trade/Special Allowance" to trade)
        
        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        
        for ((desc, amount) in items) {
            paint.color = if (isAlternate) Color.WHITE else Color.parseColor("#F9FAFB")
            paint.style = Paint.Style.FILL
            canvas.drawRect(tableLeft, currentY, tableRight, currentY + 35f, paint)
            
            // Separator line
            paint.color = Color.parseColor("#E5E7EB")
            paint.style = Paint.Style.STROKE
            canvas.drawRect(tableLeft, currentY, tableRight, currentY + 35f, paint)
            
            textPaint.color = textColor
            textPaint.textAlign = Paint.Align.LEFT
            canvas.drawText(desc, tableLeft + 20f, currentY + 22f, textPaint)
            
            textPaint.textAlign = Paint.Align.RIGHT
            textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            canvas.drawText("৳$amount", tableRight - 20f, currentY + 22f, textPaint)
            textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            
            currentY += 35f
            isAlternate = !isAlternate
        }
        
        // Draw border around the list
        paint.color = Color.parseColor("#E5E7EB")
        paint.style = Paint.Style.STROKE
        canvas.drawRoundRect(tableLeft, tableTop, tableRight, currentY, 8f, 8f, paint)
        
        // --- Summary Block ---
        currentY += 30f
        
        paint.color = ultraLightPurple
        paint.style = Paint.Style.FILL
        canvas.drawRoundRect(tableLeft, currentY, tableRight, currentY + 100f, 12f, 12f, paint)
        
        paint.color = borderPurple
        paint.style = Paint.Style.STROKE
        canvas.drawRoundRect(tableLeft, currentY, tableRight, currentY + 100f, 12f, 12f, paint)
        
        val totalGross = allowances["totalGross"] ?: 0L
        val gpf = profile.gpfDeduction
        
                val revenueStamp = 10L
        val totalDeductions = gpf + revenueStamp
        val netSalary = totalGross - gpf // The image shows Net = Gross - GPF
        
        // Gross
        textPaint.color = primaryPurple
        textPaint.textAlign = Paint.Align.LEFT
        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        textPaint.textSize = 14f
        canvas.drawText("Total Gross Salary", tableLeft + 20f, currentY + 30f, textPaint)
        textPaint.textAlign = Paint.Align.RIGHT
        canvas.drawText("৳$totalGross", tableRight - 20f, currentY + 30f, textPaint)
        
        // GPF / Deductions
        textPaint.color = red
        textPaint.textAlign = Paint.Align.LEFT
        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        canvas.drawText("GPF Deduction", tableLeft + 20f, currentY + 60f, textPaint)
        textPaint.textAlign = Paint.Align.RIGHT
        canvas.drawText("-৳${gpf}", tableRight - 20f, currentY + 60f, textPaint)
        
        // Separator line
        paint.color = borderPurple
        paint.style = Paint.Style.STROKE
        canvas.drawLine(tableLeft, currentY + 75f, tableRight, currentY + 75f, paint)
        
        // Net Salary
        textPaint.color = primaryPurple
        textPaint.textAlign = Paint.Align.LEFT
        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        textPaint.textSize = 16f
        canvas.drawText("Net Salary", tableLeft + 20f, currentY + 95f, textPaint)
        textPaint.textAlign = Paint.Align.RIGHT
        canvas.drawText("৳$netSalary", tableRight - 20f, currentY + 95f, textPaint)
    }

    fun drawFooter(
        canvas: Canvas,
        paint: Paint,
        textPaint: Paint
    ) {
        val primaryPurple = Color.parseColor("#4C1D95")
        val white = Color.WHITE
        val footerTop = 842f - 40f
        
        paint.color = primaryPurple
        paint.style = Paint.Style.FILL
        canvas.drawRect(0f, footerTop, 595f, 842f, paint)
        
        textPaint.color = white
        textPaint.textSize = 10f
        textPaint.textAlign = Paint.Align.LEFT
        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        canvas.drawText("Lnk CT Rakib", 40f, footerTop + 25f, textPaint)
        
        textPaint.textAlign = Paint.Align.RIGHT
        canvas.drawText("Salary Statement • v1.0", 555f, footerTop + 25f, textPaint)
    }
}
