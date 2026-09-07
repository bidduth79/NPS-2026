package com.example.utils.pdf

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import com.example.utils.GpfYearResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

object GpfPdfGenerator {
    suspend fun generatePdf(
        context: Context,
        results: List<GpfYearResult>
    ): File? = withContext(Dispatchers.IO) {
        try {
            val document = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
            val page = document.startPage(pageInfo)
            val canvas: Canvas = page.canvas
            
            val paint = Paint().apply {
                color = Color.BLACK
                style = Paint.Style.STROKE
                strokeWidth = 1f
            }
            
            val textPaint = Paint().apply {
                color = Color.BLACK
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                isAntiAlias = true
            }
            
            val boldPaint = Paint().apply {
                color = Color.BLACK
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                isAntiAlias = true
            }
            
            // Header
            var currentY = GpfPdfHeaderHelper.drawHeader(
                canvas = canvas,
                paint = paint,
                textPaint = textPaint,
                boldPaint = boldPaint,
                startX = 40f
            )
            
            // Table Headers
            currentY = GpfPdfTableHelper.drawTableHeaders(canvas, paint, textPaint, currentY)
            
            // Table Rows
            currentY = GpfPdfTableHelper.drawTableRows(canvas, paint, textPaint, results, currentY)
            
            // Notes
            GpfPdfTableHelper.drawNotes(canvas, paint, textPaint, boldPaint, currentY)
            
            document.finishPage(page)
            
            val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!dir.exists()) dir.mkdirs()
            
            val file = File(dir, "GPF_Statement_${System.currentTimeMillis()}.pdf")
            val outputStream = FileOutputStream(file)
            document.writeTo(outputStream)
            document.close()
            outputStream.close()
            
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "PDF saved to Downloads", Toast.LENGTH_LONG).show()
            }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Failed to generate PDF", Toast.LENGTH_SHORT).show()
            }
            null
        }
    }
}
