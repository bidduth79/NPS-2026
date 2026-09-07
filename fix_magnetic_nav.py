with open('app/src/main/java/com/example/ui/components/navigation/NavigationComponents.kt', 'r') as f:
    content = f.read()

import re

# Adding imports if not present
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
if "import android.hardware.Sensor" not in content:
    content = content.replace('import androidx.compose.ui.unit.sp\n', 'import androidx.compose.ui.unit.sp\n' + imports)

with open('app/src/main/java/com/example/ui/components/navigation/NavigationComponents.kt', 'w') as f:
    f.write(content)
