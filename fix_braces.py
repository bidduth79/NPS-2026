import re

with open('app/src/main/java/com/example/ui/components/SalaryGrowthChart.kt', 'r') as f:
    content = f.read()

# Fix the missing brace before 1 -> {
content = content.replace("                }\n                1 -> {", "                }\n                }\n                1 -> {")

# At the end of when (displayIndex), we need to close the `when` block.
# Let's check where the `when` block ends.
# It should end before the `} // Box` and `} // Surface`
content = content.replace("                6 -> {\n                    // Yearly GPF", "                6 -> {\n                    // Yearly GPF")

# Let's just do a proper replace on the end of the when block.
old_end = """                        Text("This is your forced savings for future.", color = Color(0xFF9CA3AF), fontSize = if (isCompact) 10.sp else 12.sp)
                    }
                }
            }
        }
    }
}"""
new_end = """                        Text("This is your forced savings for future.", color = Color(0xFF9CA3AF), fontSize = if (isCompact) 10.sp else 12.sp)
                    }
                }
            }
        }
    }
}"""
# Wait, I can just use a linting tool to auto-fix, or fix it manually.
