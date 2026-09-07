with open("app/src/main/java/com/example/MainActivity.kt", "r") as f:
    content = f.read()
if content.endswith("}\n"):
    with open("app/src/main/java/com/example/MainActivity.kt", "a") as f:
        f.write("}\n")
