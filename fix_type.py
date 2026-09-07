with open('app/src/main/java/com/example/ui/components/salary/FutureIncrementForecast.kt', 'r') as f:
    content = f.read()

import re
content = content.replace('grade: String,', 'grade: Int,')
content = content.replace('PayScale2026.steps[grade]', 'PayScale2026.steps[grade.toString()]')
content = content.replace('PayScale2015.steps[grade]', 'PayScale2015.steps[grade.toString()]')

with open('app/src/main/java/com/example/ui/components/salary/FutureIncrementForecast.kt', 'w') as f:
    f.write(content)
