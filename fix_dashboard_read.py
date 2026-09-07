import re

with open('app/src/main/java/com/example/ui/components/ResultDashboard.kt', 'r') as f:
    content = f.read()

# Remove the read from main block
old_logic = """    // I'll simplify the content rotation.
    val currentCardSide = (rotation / 180f).toInt()
    val contentRotation = if (currentCardSide % 2 != 0) 180f else 0f"""

new_logic = """    // We compute contentRotation inside graphicsLayer to avoid recomposition"""

content = content.replace(old_logic, new_logic)

# Insert the logic inside graphicsLayer
old_graphics = """                .graphicsLayer {
                    rotationY = contentRotation
                }"""

new_graphics = """                .graphicsLayer {
                    val currentSide = (rotation / 180f).toInt()
                    rotationY = if (currentSide % 2 != 0) 180f else 0f
                }"""

content = content.replace(old_graphics, new_graphics)

with open('app/src/main/java/com/example/ui/components/ResultDashboard.kt', 'w') as f:
    f.write(content)

