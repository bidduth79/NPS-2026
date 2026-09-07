package com.example.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import com.example.data.UserProfile
import com.example.utils.pdf.PdfHeaderHelper
import com.example.utils.pdf.PdfTableHelper
import java.io.OutputStream

object PdfGenerator {
    fun generateSalaryStatement(
        context: Context,
        outputStream: OutputStream,
        basic: Long,
        profile: UserProfile,
        allowances: Map<String, Long>,
        gradeIndex: Int,
        stepIndex: Int,
        stageName: String
    ) {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 size
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        // Paints
        val paint = Paint().apply { isAntiAlias = true }
        val textPaint = Paint().apply { isAntiAlias = true }

        // --- Draw Header ---
        val tableTop = PdfHeaderHelper.drawHeader(
            canvas = canvas,
            paint = paint,
            textPaint = textPaint,
            gradeIndex = gradeIndex,
            stepIndex = stepIndex,
            stageName = stageName
        )

        // --- Draw Table & Summary ---
        PdfTableHelper.drawTableAndSummary(
            canvas = canvas,
            paint = paint,
            textPaint = textPaint,
            basic = basic,
            profile = profile,
            allowances = allowances,
            tableTop = tableTop
        )

        // --- Draw Footer ---
        PdfTableHelper.drawFooter(
            canvas = canvas,
            paint = paint,
            textPaint = textPaint
        )

        pdfDocument.finishPage(page)
        pdfDocument.writeTo(outputStream)
        pdfDocument.close()
    }
}
