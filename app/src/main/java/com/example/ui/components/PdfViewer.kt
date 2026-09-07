package com.example.ui.components

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

val pdfMutex = Mutex()

@Composable
fun PdfViewer(resId: Int, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var pdfRenderer by remember { mutableStateOf<PdfRenderer?>(null) }
    var fileDescriptor by remember { mutableStateOf<ParcelFileDescriptor?>(null) }
    var pageCount by remember { mutableIntStateOf(0) }

    var scale by remember { mutableFloatStateOf(1f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    DisposableEffect(resId) {
        try {
            val inputStream = context.resources.openRawResource(resId)
            val file = File(context.cacheDir, "temp_gazette.pdf")
            FileOutputStream(file).use { output ->
                inputStream.copyTo(output)
            }
            
            val fd = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
            fileDescriptor = fd
            val renderer = PdfRenderer(fd)
            pdfRenderer = renderer
            pageCount = renderer.pageCount
        } catch (e: Exception) {
            e.printStackTrace()
        }

        onDispose {
            pdfRenderer?.close()
            fileDescriptor?.close()
        }
    }

    if (pdfRenderer != null) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        scale = (scale * zoom).coerceIn(1f, 5f)
                        
                        val maxOffsetX = (size.width * (scale - 1)) / 2
                        val maxOffsetY = (size.height * (scale - 1)) / 2
                        
                        offsetX = if (scale > 1f) {
                            (offsetX + pan.x).coerceIn(-maxOffsetX, maxOffsetX)
                        } else 0f
                        
                        offsetY = if (scale > 1f) {
                            (offsetY + pan.y).coerceIn(-maxOffsetY, maxOffsetY)
                        } else 0f
                    }
                }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offsetX,
                        translationY = offsetY
                    ),
                contentPadding = PaddingValues(bottom = 120.dp) // Add padding so it doesn't get hidden behind bottom nav
            ) {
                items(pageCount) { index ->
                    PdfPage(pdfRenderer = pdfRenderer!!, pageIndex = index)
                }
            }
        }
    } else {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color.White)
        }
    }
}

@Composable
fun PdfPage(pdfRenderer: PdfRenderer, pageIndex: Int) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current

    LaunchedEffect(pageIndex, pdfRenderer) {
        withContext(Dispatchers.IO) {
            pdfMutex.withLock {
                try {
                    val page = pdfRenderer.openPage(pageIndex)
                    // Use screen width to render high-res image
                    val displayMetrics = context.resources.displayMetrics
                    val width = displayMetrics.widthPixels
                    val height = (width.toFloat() / page.width.toFloat() * page.height.toFloat()).toInt()

                    val newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                    newBitmap.eraseColor(android.graphics.Color.WHITE)
                    page.render(newBitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                    page.close()
                    bitmap = newBitmap
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    if (bitmap != null) {
        Image(
            bitmap = bitmap!!.asImageBitmap(),
            contentDescription = "Page ${pageIndex + 1}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            contentScale = ContentScale.FillWidth
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.7f)
                .background(Color.White.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    }
}
