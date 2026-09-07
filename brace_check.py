with open('app/src/main/java/com/example/ui/components/SalaryGrowthChart.kt', 'r') as f:
    lines = f.readlines()

stack = []
for i, line in enumerate(lines):
    for j, char in enumerate(line):
        if char == '{':
            stack.append((i+1, j+1))
        elif char == '}':
            if stack:
                stack.pop()
            else:
                print(f"Unmatched closing brace at line {i+1}:{j+1}")

if stack:
    print("Unmatched opening braces:")
    for pos in stack:
        print(f"Line {pos[0]}:{pos[1]}")
else:
    print("All braces match perfectly!")
