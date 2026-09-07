import re

with open('app/src/main/java/com/example/ui/components/SalaryGrowthChart.kt', 'r') as f:
    content = f.read()

# Let's ensure frontAnimationProgress doesn't continuously trigger
old_launch = """    LaunchedEffect(result, displayIndex) {
        if (displayIndex == 0) {
            frontAnimationProgress.snapTo(0f)
            frontAnimationProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
            )
        }
    }"""
new_launch = """    LaunchedEffect(result, displayIndex) {
        if (displayIndex == 0) {
            frontAnimationProgress.snapTo(0f)
            frontAnimationProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
            )
        }
    }"""
content = content.replace(old_launch, new_launch)

with open('app/src/main/java/com/example/ui/components/SalaryGrowthChart.kt', 'w') as f:
    f.write(content)

