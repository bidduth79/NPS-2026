import re

def fix_file(path):
    with open(path, 'r') as f:
        content = f.read()
    
    # We want to replace `Canvas(...) { ... color = DarkBorder ... }`
    # The safest way is to add `val darkBorderColor = DarkBorder` before Canvas, 
    # but since Canvas might be inside a layout, let's just find the Canvas and prepend the val.
    
    # Actually, we can just replace `color = DarkBorder` with `color = borderColor`
    # and add `val borderColor = DarkBorder` at the beginning of the composable.
    
    content = content.replace('color = DarkBorder,', 'color = borderColor,')
    content = content.replace('Canvas(', 'val borderColor = DarkBorder\n        Canvas(')
    
    with open(path, 'w') as f:
        f.write(content)

fix_file('app/src/main/java/com/example/ui/components/chart/SalaryGraphCanvas.kt')
fix_file('app/src/main/java/com/example/ui/components/chart/BackLineChart.kt')
