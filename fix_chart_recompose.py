import re

with open('app/src/main/java/com/example/ui/components/SalaryGrowthChart.kt', 'r') as f:
    content = f.read()

old_logic = """    val normalizedRotation = (rotation % (totalStates * 360f)).let { if (it < 0f) it + (totalStates * 360f) else it }
    val displayIndex = ((normalizedRotation + 90f) / 180f).toInt() % totalStates
    
    val currentCardSide = ((rotation + 90f) / 180f).toInt()
    val contentRotation = if (currentCardSide % 2 != 0) 180f else 0f"""

new_logic = """    val displayIndex = (flipCount % totalStates).let { if (it < 0) it + totalStates else it }
    val contentRotation = if (flipCount % 2 != 0) 180f else 0f"""

content = content.replace(old_logic, new_logic)

with open('app/src/main/java/com/example/ui/components/SalaryGrowthChart.kt', 'w') as f:
    f.write(content)
