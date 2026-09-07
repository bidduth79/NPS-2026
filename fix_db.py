with open('app/src/main/java/com/example/data/AppDatabase.kt', 'r') as f:
    content = f.read()

import re

# Add TransactionEntity and increase version
content = content.replace('entities = [ChatMessageEntity::class], version = 1', 'entities = [ChatMessageEntity::class, TransactionEntity::class], version = 2')
content = content.replace('abstract fun chatDao(): ChatDao', 'abstract fun chatDao(): ChatDao\n    abstract fun transactionDao(): TransactionDao')

# Add fallbackToDestructiveMigration
content = content.replace('.build()', '.fallbackToDestructiveMigration()\n                .build()')

with open('app/src/main/java/com/example/data/AppDatabase.kt', 'w') as f:
    f.write(content)
