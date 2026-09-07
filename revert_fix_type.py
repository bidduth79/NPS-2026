with open('app/src/main/java/com/example/ui/components/salary/FutureIncrementForecast.kt', 'r') as f:
    content = f.read()

import re
content = content.replace('grade: Int,', 'grade: String,')
content = content.replace('PayScale2026.steps[grade.toString()]', 'PayScale2026.steps[grade.toInt()]')
content = content.replace('PayScale2015.steps[grade.toString()]', 'PayScale2015.steps[grade.toInt()]')

with open('app/src/main/java/com/example/ui/components/salary/FutureIncrementForecast.kt', 'w') as f:
    f.write(content)

with open('app/src/main/java/com/example/ui/components/SalaryScreen.kt', 'r') as f:
    content2 = f.read()

content2 = content2.replace('grade = selectedGrade.toInt(),', 'grade = selectedGrade,')

with open('app/src/main/java/com/example/ui/components/SalaryScreen.kt', 'w') as f:
    f.write(content2)

