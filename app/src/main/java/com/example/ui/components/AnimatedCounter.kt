package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import com.example.utils.NumberFormatter
import kotlinx.coroutines.delay

@Composable
fun AnimatedCounter(
    targetValue: Float,
    modifier: Modifier = Modifier,
    prefix: String = "",
    suffix: String = "",
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = null,
    color: Color = Color.Unspecified,
    isDecimal: Boolean = false,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    animationKey: Any? = null
) {
    var start by remember { mutableStateOf(false) }
    
    LaunchedEffect(targetValue, animationKey) {
        start = false
        delay(150)
        start = true
    }

    val animatedValue by animateFloatAsState(
        targetValue = if (start) targetValue else 0f,
        animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing),
        label = "counter"
    )

    val formattedNumber = if (isDecimal) {
        NumberFormatter.format(String.format("%.1f", animatedValue))
    } else {
        NumberFormatter.format(animatedValue.toLong())
    }

    Text(
        text = "$prefix$formattedNumber$suffix",
        modifier = modifier,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = color,
        letterSpacing = letterSpacing
    )
}
