with open('app/src/main/java/com/example/utils/pdf/PdfTableHelper.kt', 'r') as f:
    content = f.read()

import re

# Remove the calculations
content = re.sub(r'val benevolentFund = .*?150L\)', '', content)
content = re.sub(r'val groupInsurance = when.*?}\n', '', content, flags=re.DOTALL)

# Update totalDeductions
content = content.replace('val totalDeductions = gpf + benevolentFund + groupInsurance + revenueStamp', 'val totalDeductions = gpf + revenueStamp')

with open('app/src/main/java/com/example/utils/pdf/PdfTableHelper.kt', 'w') as f:
    f.write(content)
