import re

with open('app/src/main/java/com/example/ui/components/ResultDashboard.kt', 'r') as f:
    content = f.read()

old_launch = """    LaunchedEffect(activeStage) {
        val currentStage = (Math.round(flipAngle / 180f) % 4).let { if (it < 0) it + 4 else it }
        if (currentStage != activeStage) {
            var diff = activeStage - currentStage
            if (diff < 0) diff += 4
            flipAngle += diff * 180f
        }
    }"""
    
new_launch = """    LaunchedEffect(activeStage) {
        val currentStage = (Math.round(flipAngle / 180f) % 4).toInt().let { if (it < 0) it + 4 else it }
        if (currentStage != activeStage) {
            var diff = activeStage - currentStage
            if (diff < 0) diff += 4
            flipAngle += diff * 180f
        }
    }"""

content = content.replace(old_launch, new_launch)

with open('app/src/main/java/com/example/ui/components/ResultDashboard.kt', 'w') as f:
    f.write(content)
