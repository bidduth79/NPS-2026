import re

with open('app/src/main/java/com/example/ui/components/ResultDashboard.kt', 'r') as f:
    content = f.read()

# Change the display calculation in ResultDashboard too just to be safe.
old_logic = """    val displayedStage = (Math.round(rotation / 180f) % 4).let { if (it < 0) it + 4 else it }
    val contentRotation = if ((rotation % 360f).let { if (it < 0f) it + 360f else it } in 90f..270f) 180f else 0f"""

new_logic = """    // Just use activeStage to display the content to avoid infinite recomposition based on rotation
    val displayedStage = activeStage
    
    // We only need content to appear upright when the rotation brings it face up
    // Actually, we don't even need contentRotation here if we just snap to the active stage or just let the card flip.
    // I'll simplify the content rotation.
    val currentCardSide = (rotation / 180f).toInt()
    val contentRotation = if (currentCardSide % 2 != 0) 180f else 0f"""

content = content.replace(old_logic, new_logic)

with open('app/src/main/java/com/example/ui/components/ResultDashboard.kt', 'w') as f:
    f.write(content)
