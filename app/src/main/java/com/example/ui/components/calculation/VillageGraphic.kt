package com.example.ui.components.calculation

import com.example.ui.theme.DarkSlate

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp

@Composable
fun VillageGraphic(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "sway")
    val swayAngle by infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "swayAnim"
    )

    Canvas(modifier = modifier.size(60.dp, 30.dp)) {
        // Ground (River/Sand)
        drawRoundRect(
            color = Color(0xFF81D4FA),
            topLeft = Offset(0f, size.height - 4.dp.toPx()),
            size = Size(size.width, 4.dp.toPx()),
            cornerRadius = CornerRadius(2.dp.toPx())
        )
        drawRoundRect(
            color = Color(0xFFD7CCC8),
            topLeft = Offset(4.dp.toPx(), size.height - 6.dp.toPx()),
            size = Size(size.width - 8.dp.toPx(), 2.dp.toPx())
        )

        // Trees (Swaying)
        withTransform({
            rotate(swayAngle, pivot = Offset(size.width * 0.3f, size.height))
        }) {
            drawCircle(color = Color(0xFF7CB342), radius = 12.dp.toPx(), center = Offset(size.width * 0.3f, 10.dp.toPx()))
            drawCircle(color = Color(0xFF558B2F), radius = 10.dp.toPx(), center = Offset(size.width * 0.2f, 15.dp.toPx()))
        }
        withTransform({
            rotate(-swayAngle * 0.8f, pivot = Offset(size.width * 0.7f, size.height))
        }) {
            drawCircle(color = Color(0xFF689F38), radius = 14.dp.toPx(), center = Offset(size.width * 0.75f, 12.dp.toPx()))
        }

        // House 1 (Blue/Red)
        drawRect(Color(0xFF5C6BC0), topLeft = Offset(5.dp.toPx(), 18.dp.toPx()), size = Size(10.dp.toPx(), 10.dp.toPx()))
        val path1 = Path().apply {
            moveTo(5.dp.toPx(), 18.dp.toPx())
            lineTo(10.dp.toPx(), 12.dp.toPx())
            lineTo(15.dp.toPx(), 18.dp.toPx())
            close()
        }
        drawPath(path1, Color(0xFFEF5350))
        
        // House 2 (Purple/Teal)
        drawRect(Color(0xFF9575CD), topLeft = Offset(18.dp.toPx(), 20.dp.toPx()), size = Size(8.dp.toPx(), 8.dp.toPx()))
        val path2 = Path().apply {
            moveTo(18.dp.toPx(), 20.dp.toPx())
            lineTo(22.dp.toPx(), 16.dp.toPx())
            lineTo(26.dp.toPx(), 20.dp.toPx())
            close()
        }
        drawPath(path2, Color(0xFF4DB6AC))

        // House 3 (Orange/Green)
        drawRect(Color(0xFFFFB74D), topLeft = Offset(30.dp.toPx(), 16.dp.toPx()), size = Size(12.dp.toPx(), 12.dp.toPx()))
        val path3 = Path().apply {
            moveTo(30.dp.toPx(), 16.dp.toPx())
            lineTo(36.dp.toPx(), 10.dp.toPx())
            lineTo(42.dp.toPx(), 16.dp.toPx())
            close()
        }
        drawPath(path3, Color(0xFF81C784))
        
        // House 4 (Cyan/Blue)
        drawRect(Color(0xFF26C6DA), topLeft = Offset(45.dp.toPx(), 18.dp.toPx()), size = Size(10.dp.toPx(), 10.dp.toPx()))
        val path4 = Path().apply {
            moveTo(45.dp.toPx(), 18.dp.toPx())
            lineTo(50.dp.toPx(), 12.dp.toPx())
            lineTo(55.dp.toPx(), 18.dp.toPx())
            close()
        }
        drawPath(path4, Color(0xFF1565C0))
        
        // Doors
        drawRect(DarkSlate, topLeft = Offset(8.dp.toPx(), 23.dp.toPx()), size = Size(4.dp.toPx(), 5.dp.toPx()))
        drawRect(DarkSlate, topLeft = Offset(20.dp.toPx(), 24.dp.toPx()), size = Size(3.dp.toPx(), 4.dp.toPx()))
        drawRect(DarkSlate, topLeft = Offset(34.dp.toPx(), 22.dp.toPx()), size = Size(4.dp.toPx(), 6.dp.toPx()))
        drawRect(DarkSlate, topLeft = Offset(48.dp.toPx(), 23.dp.toPx()), size = Size(4.dp.toPx(), 5.dp.toPx()))
    }
}
