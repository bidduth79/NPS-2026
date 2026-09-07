with open('app/src/main/java/com/example/utils/PdfGenerator.kt', 'r') as f:
    content = f.read()

content = content.replace('"Total Deductions (Inc. GPF)"', '"GPF Deduction"')
content = content.replace('"-৳$totalDeductions"', '"-৳${gpf}"') # Assuming they want to see GPF deduction specifically. Wait, what about net salary?
content = content.replace('val netSalary = totalGross - totalDeductions', 'val netSalary = totalGross - gpf // The image shows Net = Gross - GPF')

with open('app/src/main/java/com/example/utils/PdfGenerator.kt', 'w') as f:
    f.write(content)
