import sys

with open("app/src/main/java/com/example/utils/PdfGenerator.kt", "r") as f:
    content = f.read()

print(content[:1500])
