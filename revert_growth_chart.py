import re

with open('app/src/main/java/com/example/ui/components/SalaryGrowthChart.kt', 'r') as f:
    content = f.read()

new_logic = """    // Compute the content to display based on flipCount, avoiding continuous recomposition
    val displayIndex = flipCount % totalStates
    
    // During animation, we want the content to rotate with the card.
    // The content itself needs to be flipped 180 degrees if the card is on an odd flip count.
    val contentRotation = if (flipCount % 2 != 0) 180f else 0f"""

old_logic = """    val normalizedRotation = (rotation % (totalStates * 360f)).let { if (it < 0f) it + (totalStates * 360f) else it }
    val displayIndex = ((normalizedRotation + 90f) / 180f).toInt() % totalStates
    
    val currentCardSide = ((rotation + 90f) / 180f).toInt()
    val contentRotation = if (currentCardSide % 2 != 0) 180f else 0f"""

content = content.replace(new_logic, old_logic)

with open('app/src/main/java/com/example/ui/components/SalaryGrowthChart.kt', 'w') as f:
    f.write(content)

