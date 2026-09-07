import re

with open('app/src/main/java/com/example/MainActivity.kt', 'r') as f:
    content = f.read()

# Fix the first occurrence
old_block1 = """                                                .onGloballyPositioned { coordinates -> 
                                                    // Calculate absolute position relative to the scrollable content
                                                    val topOffset = with(density) { 32.dp.toPx() }
                                                    dashboardY = coordinates.positionInRoot().y + scrollState.value - topOffset
                                                },"""

new_block1 = """                                                .onGloballyPositioned { coordinates -> 
                                                    // Calculate absolute position relative to the scrollable content
                                                    val topOffset = with(density) { 32.dp.toPx() }
                                                    val newY = coordinates.positionInRoot().y + scrollState.value - topOffset
                                                    if (kotlin.math.abs(dashboardY - newY) > 1f) {
                                                        dashboardY = newY
                                                    }
                                                },"""

# Fix the second occurrence
old_block2 = """                                        Box(modifier = Modifier.onGloballyPositioned { coordinates -> 
                                            val topOffset = with(density) { 32.dp.toPx() }
                                            dashboardY = coordinates.positionInRoot().y + scrollState.value - topOffset
                                        }) {"""

new_block2 = """                                        Box(modifier = Modifier.onGloballyPositioned { coordinates -> 
                                            val topOffset = with(density) { 32.dp.toPx() }
                                            val newY = coordinates.positionInRoot().y + scrollState.value - topOffset
                                            if (kotlin.math.abs(dashboardY - newY) > 1f) {
                                                dashboardY = newY
                                            }
                                        }) {"""

content = content.replace(old_block1, new_block1)
content = content.replace(old_block2, new_block2)

with open('app/src/main/java/com/example/MainActivity.kt', 'w') as f:
    f.write(content)

