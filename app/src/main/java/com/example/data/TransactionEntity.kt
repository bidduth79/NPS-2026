package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    val type: String, // "IN" or "OUT"
    val medium: String, // e.g. "bKash", "NAGAD", "IslamiBank"
    val date: Long,
    val rawMessage: String
)
