with open("app/src/main/java/com/example/ui/components/ResultDashboard.kt", "r") as f:
    content = f.read()

content = content.replace("3 -> \"Final Basic (2026)\"", "3 -> \"Final Basic (2027)\"")

with open("app/src/main/java/com/example/ui/components/ResultDashboard.kt", "w") as f:
    f.write(content)
