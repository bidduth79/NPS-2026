sed -i 's/skipPartiallyExpanded = true/skipPartiallyExpanded = false/' app/src/main/java/com/example/ui/components/AiChatSheet.kt
sed -i 's/containerColor = Color(0xFF1C1924),/containerColor = Color(0xAA1C1924),\n        scrimColor = Color.Black.copy(alpha = 0.3f),/' app/src/main/java/com/example/ui/components/AiChatSheet.kt
sed -i 's/\.fillMaxHeight()/.fillMaxHeight(0.95f)/' app/src/main/java/com/example/ui/components/AiChatSheet.kt
