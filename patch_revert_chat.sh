sed -i 's/contentColor = Color.White,/contentColor = Color.White,\n        contentWindowInsets = { WindowInsets.ime },/' app/src/main/java/com/example/ui/components/AiChatSheet.kt
sed -i 's/\.padding(bottom = 16\.dp)/\.padding(bottom = 16\.dp)\n                \.imePadding()/' app/src/main/java/com/example/ui/components/AiChatSheet.kt
