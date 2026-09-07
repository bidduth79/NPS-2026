sed -i 's/contentWindowInsets = { BottomSheetDefaults.windowInsets },/contentWindowInsets = { WindowInsets(0, 0, 0, 0) },/' app/src/main/java/com/example/ui/components/AiChatSheet.kt
sed -i 's/\.imePadding()/\.navigationBarsPadding()\n                \.imePadding()/' app/src/main/java/com/example/ui/components/AiChatSheet.kt
