with open('app/src/main/java/com/example/ui/components/navigation/NavigationComponents.kt', 'r') as f:
    content = f.read()

import re

# Update NavMenuItem for more pronounced magnetic bounce effect
# It already has a scale effect, let's make it a bit more pronounced if needed, but it looks good (targetValue = 0.85f).
# Let's add the Gyroscope/Accelerometer effect to the Floating Ai Button.

new_fab = """
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.DisposableEffect

@Composable
fun FloatingAiButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val accelerometer = remember { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }
    
    var tiltX by remember { mutableFloatStateOf(0f) }
    var tiltY by remember { mutableFloatStateOf(0f) }

    DisposableEffect(sensorManager, accelerometer) {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    // event.values[0] is X axis (left/right tilt)
                    // event.values[1] is Y axis (up/down tilt)
                    // Smooth the values out and limit maximum offset
                    tiltX = (it.values[0] * -1.5f).coerceIn(-15f, 15f)
                    tiltY = (it.values[1] * 1.5f).coerceIn(-15f, 15f)
                }
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }
        sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_UI)
        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    val animatedOffsetX by animateFloatAsState(
        targetValue = tiltX,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "offsetX"
    )
    val animatedOffsetY by animateFloatAsState(
        targetValue = tiltY,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "offsetY"
    )

    val fabInteractionSource = remember { MutableInteractionSource() }
    val isFabPressed by fabInteractionSource.collectIsPressedAsState()
    val fabScale by animateFloatAsState(
        targetValue = if (isFabPressed) 0.85f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "fabScale"
    )

    Box(
        modifier = modifier
            .offset(x = animatedOffsetX.dp, y = animatedOffsetY.dp)
            .size(60.dp)
            .graphicsLayer {
                scaleX = fabScale
                scaleY = fabScale
            }
            .clip(CircleShape)
            .background(GrayLight)
            .clickable(
                interactionSource = fabInteractionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.AutoAwesome,
            contentDescription = stringResource(id = R.string.chat_ai_assistant),
            tint = androidx.compose.material3.MaterialTheme.colorScheme.surface,
            modifier = Modifier.size(28.dp)
        )
    }
}
"""

# Replace the existing FloatingAiButton with the new one
content = re.sub(r'@Composable\s*fun FloatingAiButton.*?\}', new_fab, content, flags=re.DOTALL)

with open('app/src/main/java/com/example/ui/components/navigation/NavigationComponents.kt', 'w') as f:
    f.write(content)

