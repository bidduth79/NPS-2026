package com.example.ui.components

import com.example.ui.theme.AccentPurpleLight

import com.example.ui.theme.AccentPurple

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

data class TouchEffect(val id: Long, val offset: Offset)

@Composable
fun TouchSweepOverlay(content: @Composable () -> Unit) {
    val touchEffects = remember { mutableStateListOf<TouchEffect>() }
    var effectId by remember { mutableLongStateOf(0L) }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent(PointerEventPass.Initial)
                            event.changes.forEach { change ->
                                if (change.pressed && !change.previousPressed) {
                                    touchEffects.add(TouchEffect(effectId++, change.position))
                                }
                            }
                        }
                    }
                }
        ) {
            content()
        }

        // Draw sweeps on top
        touchEffects.forEach { effect ->
            SweepEffect(
                offset = effect.offset,
                onComplete = { touchEffects.remove(effect) }
            )
        }
    }
}

@Composable
fun SweepEffect(offset: Offset, onComplete: () -> Unit) {
    val anim = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        anim.animateTo(1f, tween(1200, easing = androidx.compose.animation.core.FastOutSlowInEasing))
        onComplete()
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val maxRadius = 180.dp.toPx()
        val progress = anim.value
        val radius = (0.2f + progress * 0.8f) * maxRadius // Starts small, expands smoothly
        val alpha = (1f - progress).coerceIn(0f, 1f)

        // Main smoke puff
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color.White.copy(alpha = alpha * 0.15f), // Very soft white center
                    AccentPurpleLight.copy(alpha = alpha * 0.05f), // Fades to light purple
                    Color.Transparent
                ),
                center = offset,
                radius = radius
            ),
            radius = radius,
            center = offset
        )

        // Secondary smoke puff (drifting up softly)
        val driftOffset = Offset(offset.x, offset.y - (progress * 60.dp.toPx()))
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color.White.copy(alpha = alpha * 0.1f),
                    Color.Transparent
                ),
                center = driftOffset,
                radius = radius * 0.8f
            ),
            radius = radius * 0.8f,
            center = driftOffset
        )
    }
}
