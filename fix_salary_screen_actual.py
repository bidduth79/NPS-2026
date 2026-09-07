with open('app/src/main/java/com/example/ui/components/SalaryScreen.kt', 'r') as f:
    content = f.read()

import re

# The selectedGrade variable in SalaryScreen is an Int. 
# gradeList is a list of Ints: val gradeList = PayScale2015.steps.keys.sorted().toList()
# So selectedGrade is Int.

content = content.replace('com.example.ui.components.salary.FutureIncrementForecast(\n                grade = selectedGrade,', 'com.example.ui.components.salary.FutureIncrementForecast(\n                grade = selectedGrade,')

with open('app/src/main/java/com/example/ui/components/SalaryScreen.kt', 'w') as f:
    f.write(content)
