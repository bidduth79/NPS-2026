with open('app/src/main/java/com/example/ui/components/SalaryScreen.kt', 'r') as f:
    content = f.read()

content = content.replace('grade = selectedGrade,', 'grade = selectedGrade,')

with open('app/src/main/java/com/example/ui/components/SalaryScreen.kt', 'w') as f:
    f.write(content)
