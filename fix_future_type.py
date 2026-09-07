with open('app/src/main/java/com/example/ui/components/salary/FutureIncrementForecast.kt', 'r') as f:
    content = f.read()

import re

# The `grade` parameter needs to be an Int to access PayScale maps properly, 
# and the caller (SalaryScreen) needs to pass it as an Int.
content = content.replace('grade: String,', 'grade: Int,')
content = content.replace('PayScale2026.steps[grade.toInt()]', 'PayScale2026.steps[grade]')
content = content.replace('PayScale2015.steps[grade.toInt()]', 'PayScale2015.steps[grade]')

with open('app/src/main/java/com/example/ui/components/salary/FutureIncrementForecast.kt', 'w') as f:
    f.write(content)

with open('app/src/main/java/com/example/ui/components/SalaryScreen.kt', 'r') as f:
    content2 = f.read()

content2 = content2.replace('grade = selectedGrade,', 'grade = selectedGrade,')

with open('app/src/main/java/com/example/ui/components/SalaryScreen.kt', 'w') as f:
    f.write(content2)
