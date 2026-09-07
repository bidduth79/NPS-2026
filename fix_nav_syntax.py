with open('app/src/main/java/com/example/ui/components/navigation/NavigationComponents.kt', 'r') as f:
    content = f.read()

import re

# Remove the incorrectly placed imports
content = re.sub(r'import android.content.Context\s*import android.hardware.Sensor\s*import android.hardware.SensorEvent\s*import android.hardware.SensorEventListener\s*import android.hardware.SensorManager\s*import androidx.compose.ui.platform.LocalContext\s*import androidx.compose.runtime.DisposableEffect\s*import androidx.compose.runtime.mutableFloatStateOf', '', content)

# Add them at the top
imports = """
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableFloatStateOf
"""
content = content.replace('package com.example.ui.components.navigation\n', 'package com.example.ui.components.navigation\n' + imports)

with open('app/src/main/java/com/example/ui/components/navigation/NavigationComponents.kt', 'w') as f:
    f.write(content)
