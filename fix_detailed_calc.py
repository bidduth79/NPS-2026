with open('app/src/main/java/com/example/ui/components/salary/DetailedCalculationCard.kt', 'r') as f:
    content = f.read()

import re

# Remove the calculations
content = re.sub(r'val benevolentFund = .*?150L\)', '', content, flags=re.DOTALL)
content = re.sub(r'// Standard BD Govt Group Insurance.*?val groupInsurance = when.*?}\n', '', content, flags=re.DOTALL)

# Update totalDeductions
content = content.replace('val totalDeductions = gpf + benevolentFund + groupInsurance + revenueStamp', 'val totalDeductions = gpf + revenueStamp')

# Remove rendering
content = re.sub(r'SalaryRowItem\("Benevolent Fund.*?isBold = false\)\n', '', content)
content = re.sub(r'SalaryRowItem\("Group Insurance.*?isBold = false\)\n', '', content)

with open('app/src/main/java/com/example/ui/components/salary/DetailedCalculationCard.kt', 'w') as f:
    f.write(content)
