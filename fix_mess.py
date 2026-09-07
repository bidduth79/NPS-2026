with open('app/src/main/java/com/example/ui/components/chart/SalaryGraphCanvas.kt', 'r') as f:
    content = f.read()
content = content.replace("fun SalaryGraphval borderColor = DarkBorder\n        Canvas(result: CalculationResult) {", "fun SalaryGraphCanvas(result: CalculationResult) {")
with open('app/src/main/java/com/example/ui/components/chart/SalaryGraphCanvas.kt', 'w') as f:
    f.write(content)
