import re

with open('app/src/main/java/com/example/ui/components/SalaryGrowthChart.kt', 'r') as f:
    content = f.read()

# Instead of recalculating layout continuously or having infinite recompositions
# displayIndex might be doing something crazy during animation, let's fix it.
# If rotation is animating continuously, displayIndex is changing continuously.
# This causes the when(displayIndex) block to switch composables rapidly during animation,
# forcing massive recompositions and hanging the app.

# Solution:
# We should only change the content of the card when it is flipped away (contentRotation is 180 or close to 90)
# or we can use derivedStateOf for the active index, but a simpler way is to base the content index directly on flipCount.

# We want the content to change only when the card is face down (rotation = 90, 270, etc).
# Actually, flipCount is an integer. 
# Target rotation is flipCount * 180f.
# When flipCount is 0 -> index 0 (rotation 0)
# When flipCount is 1 -> index 1 (rotation 180)
# When flipCount is 2 -> index 2 (rotation 360)

# But wait, when the card is flipped (180 deg), the content needs to be rotated 180 deg to be right-side up.
# So contentRotation = if (flipCount % 2 != 0) 180f else 0f.
# And the displayIndex is simply `(flipCount % totalStates)`.

# This way, displayIndex doesn't change 60 times a second during animation, it changes once instantly upon click.
# And we just animate the rotation.

old_logic = """    val normalizedRotation = (rotation % (totalStates * 360f)).let { if (it < 0f) it + (totalStates * 360f) else it }
    val displayIndex = ((normalizedRotation + 90f) / 180f).toInt() % totalStates
    
    val currentCardSide = ((rotation + 90f) / 180f).toInt()
    val contentRotation = if (currentCardSide % 2 != 0) 180f else 0f"""

new_logic = """    // Compute the content to display based on flipCount, avoiding continuous recomposition
    val displayIndex = flipCount % totalStates
    
    // During animation, we want the content to rotate with the card.
    // The content itself needs to be flipped 180 degrees if the card is on an odd flip count.
    val contentRotation = if (flipCount % 2 != 0) 180f else 0f"""

content = content.replace(old_logic, new_logic)

with open('app/src/main/java/com/example/ui/components/SalaryGrowthChart.kt', 'w') as f:
    f.write(content)

