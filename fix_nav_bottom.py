with open('app/src/main/java/com/example/ui/components/navigation/NavigationComponents.kt', 'r') as f:
    content = f.read()

import re
# Looks like the old FloatingAiButton wasn't replaced properly, but appended instead. Let's fix it by extracting everything up to the first FloatingAiButton, and adding our new one cleanly.

# Find where the first @Composable fun FloatingAiButton starts
match = re.search(r'@Composable\s*fun FloatingAiButton', content)
if match:
    # Get everything before it
    base_content = content[:match.start()]
    
    new_fab = """@Composable
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
    
    with open('app/src/main/java/com/example/ui/components/navigation/NavigationComponents.kt', 'w') as f:
        f.write(base_content + new_fab)

