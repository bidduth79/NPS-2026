with open('app/src/main/java/com/example/utils/SalaryBreakdownHelper.kt', 'r') as f:
    content = f.read()

# Update medical allowance based on scale
content = content.replace(
    'val medicalAllowance = 1500L',
    'val medicalAllowance = if (profile.allowanceBaseScale == "2026") 3000L else 1500L'
)

# Update education allowance based on scale
content = content.replace(
    'val educationAllowance = profile.numberOfChildren * 500L',
    'val educationAllowance = profile.numberOfChildren * (if (profile.allowanceBaseScale == "2026") 1000L else 500L)'
)

# Update other allowances (tiffin etc) based on scale
content = content.replace(
    '(if (profile.hasTiffinAllowance) 200L else 0L) +',
    '(if (profile.hasTiffinAllowance) (if (profile.allowanceBaseScale == "2026") 600L else 200L) else 0L) +'
)

with open('app/src/main/java/com/example/utils/SalaryBreakdownHelper.kt', 'w') as f:
    f.write(content)
