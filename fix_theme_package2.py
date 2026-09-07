with open('app/src/main/java/com/example/ui/theme/Theme.kt', 'r') as f:
    lines = f.readlines()

if 'package' not in lines[0]:
    # find package line
    pkg_idx = next(i for i, line in enumerate(lines) if 'package com.example.ui.theme' in line)
    # swap
    pkg_line = lines[pkg_idx]
    lines.pop(pkg_idx)
    lines.insert(0, pkg_line)

with open('app/src/main/java/com/example/ui/theme/Theme.kt', 'w') as f:
    f.writelines(lines)
